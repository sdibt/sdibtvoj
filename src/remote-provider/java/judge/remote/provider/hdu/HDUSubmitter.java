package judge.remote.provider.hdu;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Base64;
import java.io.UnsupportedEncodingException;

import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.HttpStatusValidator;
import judge.httpclient.SimpleNameValueEntityFactory;
import judge.remote.RemoteOjInfo;
import judge.remote.account.RemoteAccount;
import judge.remote.submitter.CanonicalSubmitter;
import judge.remote.submitter.SubmissionInfo;

import org.apache.http.HttpEntity;
import org.springframework.stereotype.Component;

@Component
public class HDUSubmitter extends CanonicalSubmitter {

    @Override
    public RemoteOjInfo getOjInfo() {
        return HDUInfo.INFO;
    }

    @Override
    protected boolean needLogin() {
        return true;
    }

    @Override
    protected Integer getMaxRunId(SubmissionInfo info, DedicatedHttpClient client, boolean submitted) {
        String html = client.get("/status.php?user=" + info.remoteAccountId + "&pid=" + info.remoteProblemId).getBody();
        Matcher matcher = Pattern.compile("<td height=22px>(\\d+)").matcher(html);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;
    }

    @Override
    protected String submitCode(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) {
       
	String willSubmitCodeString;
        try {
	    willSubmitCodeString = Base64.getEncoder().encodeToString(URLEncoder.encode(info.sourceCode,"utf-8").getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
	    willSubmitCodeString = "";
	}
	HttpEntity entity = SimpleNameValueEntityFactory.create( //
            "check", "0", //
            "language", info.remotelanguage, //
            "problemid", info.remoteProblemId, //
            "_usercode", willSubmitCodeString, //
            getCharset() //
        );

        client.post("/submit.php?action=submit", entity, HttpStatusValidator.SC_MOVED_TEMPORARILY);
        return null;
    }

}


package judge.remote.provider.sdut;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class SDUTSubmitter extends CanonicalSubmitter {

    @Override
    public RemoteOjInfo getOjInfo() {
        return SDUTInfo.INFO;
    }

    @Override
    protected boolean needLogin() {
        return true;
    }

    @Override
    protected Integer getMaxRunId(SubmissionInfo info, DedicatedHttpClient client, boolean submitted) {
        String html = client.get("/onlinejudge2/index.php/Home/Solution/status?username=" + info.remoteAccountId + "&pid=" + info.remoteProblemId).getBody();
        Matcher matcher = Pattern.compile("<tr>[\\s\\S]+?<td>(\\d+?)</td>[\\s\\S]+?<td><a href=\"/onlinejudge2/index.php/Home/User/info/uid/\\d+?\\.html").matcher(html);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;
    }

    @Override
    protected String submitCode(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) {
            String   lang="0";
            if(info.remotelanguage.equals("0"))
                    lang="gcc";
            if(info.remotelanguage.equals("1"))
                    lang="g++";
            if(info.remotelanguage.equals("2"))
                    lang="java";
            if(info.remotelanguage.equals("3"))
                    lang="python2";
            if(info.remotelanguage.equals("4"))
                    lang="python3";

            HttpEntity entity = SimpleNameValueEntityFactory.create(
            "pid", info.remoteProblemId, //
            "lang", lang, //
           // "lang", info.remotelanguage, //
            "code", info.sourceCode
        );
        System.out.println(info.remotelanguage);
        client.post("/onlinejudge2/index.php/Home/Solution/submitsolution", entity, HttpStatusValidator.SC_MOVED_TEMPORARILY);
        return null;
    }

}

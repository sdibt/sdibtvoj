package judge.remote.provider.bailian;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.HttpStatusValidator;
import judge.httpclient.SimpleNameValueEntityFactory;
import judge.remote.RemoteOjInfo;
import judge.remote.account.RemoteAccount;
import judge.remote.submitter.CanonicalSubmitter;
import judge.remote.submitter.SubmissionInfo;
import judge.tool.Tools;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.springframework.stereotype.Component;

@Component
public class BAILIANSubmitter extends CanonicalSubmitter {

    @Override
    public RemoteOjInfo getOjInfo() {
        return BAILIANInfo.INFO;
    }

    @Override
    protected boolean needLogin() {
        return true;
    }

    @Override
    protected Integer getMaxRunId(SubmissionInfo info, DedicatedHttpClient client, boolean submitted) {
        String html1 = client.get ("/").getBody();
        String nickname = Tools.regFind(html1,"<dd><a href=\"http://openjudge.cn/user/\\d+?/\">(\\s*\\S*)</a></dd>");
        String html = client.get("/practice/status/?userName=" + nickname ).getBody();
        Matcher matcher = Pattern.compile("<td class=\"language\"><a href=\"/practice/solution/(\\d+?)/.*?\"").matcher(html);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;
    }

    @Override
    protected String submitCode(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) {
            String   lang="0";
            if(info.remotelanguage.equals("0"))
                    lang="G++";
            if(info.remotelanguage.equals("1"))
                    lang="GCC";
            if(info.remotelanguage.equals("2"))
                    lang="Java";
            if(info.remotelanguage.equals("3"))
                    lang="Pascal";
            if(info.remotelanguage.equals("4"))
                    lang="Python2";
            if(info.remotelanguage.equals("5"))
                    lang="Python3";
            if(info.remotelanguage.equals("6"))
                    lang="C#";



            HttpEntity entity = SimpleNameValueEntityFactory.create(
            "problemNumber", info.remoteProblemId, //
            "language", lang, //
            "source",  new String(Base64.encodeBase64(info.sourceCode.getBytes())),
            "sourceEncode", "base64",
            "contestId","3"
        );
        client.post("/api/solution/submit/", entity, HttpStatusValidator.SC_OK);
             return null;
    }

}

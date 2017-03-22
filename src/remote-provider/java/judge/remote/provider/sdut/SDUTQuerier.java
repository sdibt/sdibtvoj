package judge.remote.provider.sdut;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import judge.httpclient.DedicatedHttpClient;
import judge.remote.RemoteOjInfo;
import judge.remote.account.RemoteAccount;
import judge.remote.querier.AuthenticatedQuerier;
import judge.remote.status.RemoteStatusType;
import judge.remote.status.SubmissionRemoteStatus;
import judge.remote.status.SubstringNormalizer;
import judge.remote.submitter.SubmissionInfo;
import judge.tool.Tools;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

@Component
public class SDUTQuerier extends AuthenticatedQuerier {

    @Override
    public RemoteOjInfo getOjInfo() {
        return SDUTInfo.INFO;
    }

    @Override
    protected SubmissionRemoteStatus query(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) {
        String html = client.get("/onlinejudge2/index.php/Home/Solution/status?runid=" + info.remoteRunId).getBody();
        Pattern pattern = Pattern.compile(info.remoteRunId +"</td>[\\s\\S]+?<td><a href=\".+?\">.+?</a></td>[\\s\\S]+?<td><a href=\".+?\">\\d+?</a></td>[\\s\\S]+?<td class=\".+?\">(.+?)</td>[\\s\\S]+?<td>(\\d+?)ms</td>[\\s\\S]+?<td>(\\d+?)kb</td>");
        Matcher matcher = pattern.matcher(html);
        Validate.isTrue(matcher.find());
        
        SubmissionRemoteStatus status = new SubmissionRemoteStatus();
        status.rawStatus = matcher.group(1).replaceAll("<.*?>", "").replaceAll("-[0-9]*","").replaceAll("\\*","").replace('_', ' ').trim();
        status.statusType = SubstringNormalizer.DEFAULT.getStatusType(status.rawStatus);
        if (status.statusType == RemoteStatusType.AC) {
            status.executionMemory = Integer.parseInt(matcher.group(3).replaceAll("\\D", ""));
            status.executionTime = Integer.parseInt(matcher.group(2).replaceAll("\\D", ""));
        } else if (status.statusType == RemoteStatusType.CE) {
            html = client.get("/onlinejudge2/index.php/Home/Compile/view/sid/" + info.remoteRunId).getBody();
            status.compilationErrorInfo = Tools.regFind(html, "<pre>([.\\s\\S]+?)</pre>");
        }
        return status;
    }

}

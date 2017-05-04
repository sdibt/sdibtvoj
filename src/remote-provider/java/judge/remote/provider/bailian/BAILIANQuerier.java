package judge.remote.provider.bailian;

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
public class BAILIANQuerier extends AuthenticatedQuerier {

    @Override
    public RemoteOjInfo getOjInfo() {
        return BAILIANInfo.INFO;
    }

    @Override
    protected SubmissionRemoteStatus query(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) {
        String html = client.get("/practice/solution/" + info.remoteRunId).getBody();
        Pattern pattern = Pattern.compile(info.remoteRunId + "/.+?class=\"result-.+?\">(.+?)</a></td>[\\s\\S]+?<td class=\"memory\">(.*?)kB</td>\\s+?<td class=\"spending-time\">(.*?)ms</td>");
        Matcher matcher = pattern.matcher(html);
        Validate.isTrue(matcher.find());
        
        SubmissionRemoteStatus status = new SubmissionRemoteStatus();
        status.rawStatus = matcher.group(1).replaceAll("<.*?>", "").replaceAll("-[0-9]*","").replaceAll("\\*","").replace('_', ' ').trim();
        status.statusType = SubstringNormalizer.DEFAULT.getStatusType(status.rawStatus);
        if (status.statusType == RemoteStatusType.AC) {
            status.executionMemory = Integer.parseInt(matcher.group(2).replaceAll("\\D", ""));
            status.executionTime = Integer.parseInt(matcher.group(3).replaceAll("\\D", ""));
        } else if (status.statusType == RemoteStatusType.CE) {
            html = client.get("/practice/solution/" + info.remoteRunId).getBody();
            status.compilationErrorInfo = Tools.regFind(html, "(<pre>[\\s\\S]+?</pre>)");
        }
        return status;
    }

}

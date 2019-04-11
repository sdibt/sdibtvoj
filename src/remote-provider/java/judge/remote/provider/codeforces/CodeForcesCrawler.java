package judge.remote.provider.codeforces;

import judge.remote.RemoteOjInfo;
import judge.remote.crawler.RawProblemInfo;
import judge.remote.crawler.SimpleCrawler;
import judge.remote.shared.codeforces.CFStyleCrawler;
import judge.tool.Tools;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

@Component
public class CodeForcesCrawler extends CFStyleCrawler {

    @Override
    public RemoteOjInfo getOjInfo() {
        return CodeForcesInfo.INFO;
    }

    @Override
    protected String getProblemUrl(String problemId) {
        String contestNum = problemId.replaceAll("\\D.*", "");
        String problemNum = problemId.replaceAll("^\\d*", "");
        return getHost().toURI() + "/problemset/problem/" + contestNum + "/" + problemNum;
    }

    @Override
    protected void preValidate(String problemId) {
        Validate.isTrue(problemId.matches("[1-9]\\w*"));
        Validate.isTrue(problemId.toUpperCase().equals(problemId));
    }


}

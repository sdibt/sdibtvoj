package judge.remote.provider.hust;

import judge.remote.RemoteOjInfo;
import judge.remote.crawler.RawProblemInfo;
import judge.remote.crawler.SimpleCrawler;
import judge.tool.Tools;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

@Component
public class HUSTCrawler extends SimpleCrawler {

    @Override
    public RemoteOjInfo getOjInfo() {
        return HUSTInfo.INFO;
    }

    @Override
    protected String getProblemUrl(String problemId) {
        return getHost().toURI() + "/problem/show/" + problemId;
    }
    
    @Override
    protected void preValidate(String problemId) {
        Validate.isTrue(problemId.matches("[1-9]\\d*"));
    }

    @Override
    protected void populateProblemInfo(RawProblemInfo info, String problemId, String html) {
        info.title = Tools.regFind(html, "<title>([\\s\\S]*?)</title>").trim();
        info.timeLimit = ((int) (1000 * Double.parseDouble(Tools.regFind(html, "Time Limit: <span class=\"label label-warning\">(.+)s</span>"))));
        info.memoryLimit = ((int) (1024 * Double.parseDouble(Tools.regFind(html, "Memory Limit: <span class=\"label label-danger\">(.+)MB</span>"))));
        info.description = (Tools.regFind(html, "<dd id=\"problem-desc\">([\\s\\S]*?)</dd>"));
        info.input = (Tools.regFind(html, "<dt> Input </dt>\\s*<dd>([\\s\\S]*?)</dd>"));
        info.output = (Tools.regFind(html, "<dt> Output </dt>\\s*<dd>([\\s\\S]*?)</dd>"));
        info.sampleInput = (Tools.regFind(html, "<dt> Sample Input </dt>\\s*<dd>([\\s\\S]*?)</dd>"));
        info.sampleOutput = (Tools.regFind(html, "<dt> Sample Output </dt>\\s*<dd>([\\s\\S]*?)</dd>"));
        info.hint = (Tools.regFind(html, "<dt> Hint </dt>\\s*<dd>([\\s\\S]*?)</dd>"));
        String temp = (Tools.regFind(html, "<dt> Source </dt>\\s*<dd>([\\s\\S]*?)</dd>").trim());
        info.source = ("<a href=\"http://acm.hust.edu.cn/problem/search?text=" + temp + "&area=source\">" + temp + "</a>");
    }

}

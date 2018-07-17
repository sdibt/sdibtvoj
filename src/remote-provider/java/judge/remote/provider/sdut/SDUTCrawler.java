package judge.remote.provider.sdut;

import judge.remote.RemoteOjInfo;
import judge.remote.crawler.RawProblemInfo;
import judge.remote.crawler.SimpleCrawler;
import judge.tool.Tools;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

@Component
public class SDUTCrawler extends SimpleCrawler {

    @Override
    public RemoteOjInfo getOjInfo() {
        return SDUTInfo.INFO;
    }

    @Override
    protected String getProblemUrl(String problemId) {
        return getHost().toURI() + "/onlinejudge2/index.php/Home/Index/problemdetail/pid/" + problemId + ".html";
    }
    
    @Override
    protected void preValidate(String problemId) {
        Validate.isTrue(problemId.matches("[1-9]\\d*"));
    }

    @Override
    protected void populateProblemInfo(RawProblemInfo info, String problemId, String html) {

        info.title = Tools.regFind(html, "<h3 class=\"problem-header\">(.+?)</h3>").trim();
        
        info.timeLimit = (Integer.parseInt(Tools.regFind(html, "<span class=\"user-black\">Time Limit:&nbsp;(\\d+?)[\\s]+?ms</span>")));
        
        info.memoryLimit = (Integer.parseInt(Tools.regFind(html, "<span class=\"user-black\">Memory Limit:&nbsp;(\\d+?)[\\s]+?KiB</span>")));
        
        info.description = (Tools.regFind(html, "<h4>Problem Description</h4>[\\s\\S]*?(<div class=\"prob-content\">[.\\s\\S]+?</div>[\\s\\S]*?)<h4>Input"));
        
        info.input = (Tools.regFind(html, "<h4>Input</h4>[\\s\\S]*?(<div class=\"prob-content\">[.\\s\\S]+?</div>[\\s\\S]*?)<h4>Output"));
        
        info.output = (Tools.regFind(html, "<h4>Output</h4>[\\s\\S]*?(<div class=\"prob-content\">[.\\s\\S]+?</div>[\\s\\S]*?)<h4>Sample Input"));
        
        info.sampleInput = (Tools.regFind(html, "<h4>Sample Input</h4>[\\s\\S]*?<div class=\"prob-content\">[\\s\\S]*?(<pre>[.\\s\\S]+?</pre>)[\\s\\S]*?</div>[\\s\\S]*?<h4>Sample Output"));
        
        info.sampleOutput = (Tools.regFind(html, "<h4>Sample Output</h4>[\\s\\S]*?<div class=\"prob-content\">[\\s\\S]*?(<pre>[.\\s\\S]+?</pre>)[\\s\\S]*?</div>[\\s\\S]*?<h4>Hint"));
        
        info.hint = (Tools.regFind(html, "<h2>Hint</h2>([\\s\\S]*?)<h2>Source</h2>"));
        
        info.source = (Tools.regFind(html, "<h4>Author</h4>[\\s\\S]*?<div class=\"prob-content\">([.\\s\\S]+?)</div>"));
    }

}

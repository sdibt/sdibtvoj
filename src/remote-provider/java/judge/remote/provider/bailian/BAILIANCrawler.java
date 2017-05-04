package judge.remote.provider.bailian;

import judge.remote.RemoteOjInfo;
import judge.remote.crawler.RawProblemInfo;
import judge.remote.crawler.SimpleCrawler;
import judge.tool.Tools;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

@Component
public class BAILIANCrawler extends SimpleCrawler {

    @Override
    public RemoteOjInfo getOjInfo() {
        return BAILIANInfo.INFO;
    }

    @Override
    protected String getProblemUrl(String problemId) {

        return getHost().toURI() + "/practice/" + problemId ;
    }
    
    @Override
    protected void preValidate(String problemId) {
        Validate.isTrue(problemId.matches("[1-9]\\d*"));
    }

    @Override
    protected void populateProblemInfo(RawProblemInfo info, String problemId, String html) {
           
        info.title = Tools.regFind(html, "<div id=\"pageTitle\"><h2>\\d+?:([\\s\\S]+?)</h2></div>").trim();
    
        info.timeLimit = (Integer.parseInt(Tools.regFind(html, "<dd>\\s*(\\d+)ms\\s*</dd>")));
        info.memoryLimit = (Integer.parseInt(Tools.regFind(html, "<dd>\\s*(\\d*)kB\\s*</dd>")));
        info.description = (Tools.regFind(html, "<dt>\\s*描述\\s*</dt><dd>([\\s\\S]+?)</dd><dt>\\s*输入"));
        info.input = (Tools.regFind(html, "<dt>\\s*输入\\s*</dt><dd>([\\s\\S]+?)</dd><dt>\\s*输出"));
        info.output = (Tools.regFind(html, "<dt>\\s*输出\\s*</dt><dd>([\\s\\S]+?)</dd><dt>\\s*样例输入"));
        info.sampleInput = (Tools.regFind(html, "<dt>\\s*样例输入\\s*</dt><dd>(<pre>[\\s\\S]+?</pre>)</dd><dt>\\s*样例输出"));
        info.sampleOutput = (Tools.regFind(html, "<dt>\\s*样例输出\\s*</dt><dd>(<pre>[\\s\\S]+?</pre>)</dd>"));
        info.hint = (Tools.regFind(html, "<dt>\\s*提示\\s*</dt><dd>([\\s\\S]+?)</dd>"));
        info.source = (Tools.regFind(html, "<dt>\\s*来源\\s*</dt><dd>([\\s\\S]+?)</dd>").trim());
    }

}

package judge.remote.provider.codeforces;

import java.util.HashMap;
import java.util.LinkedHashMap;

import judge.remote.RemoteOjInfo;
import judge.remote.language.LanguageFinder;
import judge.remote.shared.codeforces.CFStyleLanguageFinder;
import judge.tool.Handler;

import org.springframework.stereotype.Component;

@Component
public class CodeForcesLanguageFinder extends CFStyleLanguageFinder {

    @Override
    public RemoteOjInfo getOjInfo() {
        return CodeForcesInfo.INFO;
    }

}

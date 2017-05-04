package judge.remote.provider.bailian;

import java.util.HashMap;
import java.util.LinkedHashMap;

import judge.remote.RemoteOjInfo;
import judge.remote.language.LanguageFinder;
import judge.tool.Handler;

import org.springframework.stereotype.Component;

@Component
public class BAILIANLanguageFinder implements LanguageFinder {

    @Override
    public RemoteOjInfo getOjInfo() {
        return BAILIANInfo.INFO;
    }

    @Override
    public boolean isDiverse() {
        return false;
    }

    @Override
    public void getLanguages(String remoteProblemId, Handler<LinkedHashMap<String, String>> handler) {
        // TODO Auto-generated method stub
    }

    @Override
    public LinkedHashMap<String, String> getDefaultLanguages() {
        LinkedHashMap<String, String> languageList = new LinkedHashMap<String, String>();
        languageList.put("0", "G++(5.3)");
        languageList.put("1", "GCC(5.3)");
        languageList.put("2", "Java(OpenJDK9)");
        languageList.put("3", "Pascal(FreePascal)");
        languageList.put("4", "Python2(2.7)");
        languageList.put("5", "Python3(3.5)");
        languageList.put("6", "C#(mono4.2)");
        return languageList;
    }

    @Override
    public HashMap<String, String> getLanguagesAdapter() {
        // TODO Auto-generated method stub
        return null;
    }

}

package judge.remote.provider.codeforces;

import java.util.HashMap;
import java.util.LinkedHashMap;

import judge.remote.RemoteOjInfo;
import judge.remote.language.LanguageFinder;
import judge.tool.Handler;

import org.springframework.stereotype.Component;

@Component
public class CodeForcesLanguageFinder implements LanguageFinder {

    @Override
    public RemoteOjInfo getOjInfo() {
        return CodeForcesInfo.INFO;
    }

    @Override
    public boolean isDiverse() {
        return true;
    }

    @Override
    public void getLanguages(String remoteProblemId, Handler<LinkedHashMap<String, String>> handler) {
        // TODO Auto-generated method stub
    }

    @Override
    public LinkedHashMap<String, String> getDefaultLanguages() {
        LinkedHashMap<String, String> languageList = new LinkedHashMap<>();
        languageList.put("43", "GNU GCC C11 5.1.0");
        languageList.put("52", "Clang++17 Diagnostics");
        languageList.put("42", "GNU G++11 5.1.0");
        languageList.put("50", "GNU G++14 6.4.0");
        languageList.put("54", "GNU G++17 7.3.0");
        languageList.put("2", "Microsoft Visual C++ 2010");
        languageList.put("59", "Microsoft Visual C++ 2017");
        languageList.put("9", "C# Mono 5.18");
        languageList.put("28", "D DMD32 v2.083.1");
        languageList.put("32", "Go 1.11.4");
        languageList.put("12", "Haskell GHC 8.6.3");
        languageList.put("36", "Java 1.8.0_162");
        languageList.put("48", "Kotlin 1.3.10");
        languageList.put("19", "OCaml 4.02.1");
        languageList.put("3", "Delphi 7");
        languageList.put("4", "Free Pascal 3.0.2");
        languageList.put("51", "PascalABC.NET 3.4.2");
        languageList.put("13", "Perl 5.20.1");
        languageList.put("6", "PHP 7.2.13");
        languageList.put("7", "Python 2.7.15");
        languageList.put("31", "Python 3.7.2");
        languageList.put("40", "PyPy 2.7 (6.0.0)");
        languageList.put("41", "PyPy 3.5 (6.0.0)");
        languageList.put("8", "Ruby 2.0.0p645");
        languageList.put("49", "Rust 1.31.1");
        languageList.put("20", "Scala 2.12.8");
        languageList.put("34", "JavaScript V8 4.8.0");
        languageList.put("55", "Node.js 9.4.0");
        return languageList;
    }

    @Override
    public HashMap<String, String> getLanguagesAdapter() {
        // TODO Auto-generated method stub
        return null;
    }

}

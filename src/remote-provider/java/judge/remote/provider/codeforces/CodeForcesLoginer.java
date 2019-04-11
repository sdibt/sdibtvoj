package judge.remote.provider.codeforces;


import judge.remote.RemoteOjInfo;


import judge.remote.shared.codeforces.CFStyleLoginer;
import org.springframework.stereotype.Component;

@Component
public class CodeForcesLoginer extends CFStyleLoginer {

    @Override
    public RemoteOjInfo getOjInfo() {
        return CodeForcesInfo.INFO;
    }

}

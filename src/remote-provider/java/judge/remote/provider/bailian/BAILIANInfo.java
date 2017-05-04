package judge.remote.provider.bailian;

import judge.remote.RemoteOj;
import judge.remote.RemoteOjInfo;

import org.apache.http.HttpHost;

public class BAILIANInfo {

    public static final RemoteOjInfo INFO = new RemoteOjInfo( //
            RemoteOj.BAILIAN, //
            "BAILIAN", //
            new HttpHost("bailian.openjudge.cn") //
    );
    
    static {
        INFO.faviconUrl = "images/remote_oj/BAILIAN_icon.jpg";
        INFO._64IntIoFormat = "%lld & %llu";
    }

}

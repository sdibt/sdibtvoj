package judge.remote.provider.sdibt;

import judge.remote.RemoteOj;
import judge.remote.RemoteOjInfo;

import org.apache.http.HttpHost;

public class SDIBTInfo {

    public static final RemoteOjInfo INFO = new RemoteOjInfo( //
            RemoteOj.SDIBT, //
            "SDIBT", //
            new HttpHost("acm.sdtbu.edu.cn", 443, "https") //
    );
    
    static {
        INFO.faviconUrl = "images/remote_oj/SDIBT_favicon.ico";
        INFO._64IntIoFormat = "%lld & %llu";
    }

}

package judge.remote.provider.sdut;

import judge.remote.RemoteOj;
import judge.remote.RemoteOjInfo;

import org.apache.http.HttpHost;

public class SDUTInfo {

    public static final RemoteOjInfo INFO = new RemoteOjInfo( //
            RemoteOj.SDUT, //
            "SDUT", //
            new HttpHost("acm.sdut.edu.cn") //
    );
    
    static {
        INFO.faviconUrl = "images/remote_oj/SDUT_favicon.ico";
        INFO._64IntIoFormat = "%lld & %llu";
    }

}

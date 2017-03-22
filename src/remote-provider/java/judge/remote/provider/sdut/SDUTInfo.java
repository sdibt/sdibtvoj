package judge.remote.provider.sdut;

import judge.remote.RemoteOj;
import judge.remote.RemoteOjInfo;

import org.apache.http.HttpHost;

public class SDUTInfo {

    public static final RemoteOjInfo INFO = new RemoteOjInfo( //
            RemoteOj.SDUT, //
            "SDUT", //
            new HttpHost("www.sdutacm.org") //
    );
    
    static {
        INFO.faviconUrl = "images/remote_oj/SDUT_favicon.ico";
        INFO._64IntIoFormat = "%lld & %llu";
    }

}

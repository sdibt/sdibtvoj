package judge.remote.shared;

import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

import org.apache.http.HttpStatus;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

public class FileDownloader {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
    private static final long NOT_REDOWNLOAD_IN = 1000L * 60 * 60 * 1;//an hour
    private static  SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH);
    private final static Logger log = LoggerFactory.getLogger(FileDownloader.class);
    private static final int TRY_TIMES = 5;

    static {
        dateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
    }

    public static void downLoadFromUrl(String urlStr,String savePath,String fileName) throws Exception{
        log.info(urlStr);
        FileOutputStream fos = null;
        try {
            if (urlStr.contains("https")) {
                enableTLSv2();
            }
            Connection connection = Jsoup.connect(urlStr)
                    .header("Referer",urlStr.substring(0,urlStr.indexOf("/", "https://".length())))
                    .userAgent(USER_AGENT)
                    .timeout(10000)
                    .ignoreContentType(true);

            File saveDir = new File(savePath);
            if(!saveDir.exists()){
                saveDir.mkdirs();
            }
            File file = new File(saveDir+File.separator+fileName);
            if(file.exists()){
                Date lastModified = new Date(file.lastModified());
                log.info("already downloaded at " + lastModified.toString());
                if((new Date()).getTime() - lastModified.getTime() <  NOT_REDOWNLOAD_IN){
                    log.info("ignore");
                    return ;
                }
                connection.header("If-Modified-Since", dateFormat.format(lastModified));
            }
            int tryTimesLeft = TRY_TIMES;
            Response response = null;
            while(--tryTimesLeft >= 0){
                try{
                    response = connection.execute();
                } catch (HttpStatusException e){
                    if(e.getStatusCode() == HttpStatus.SC_NOT_MODIFIED){
                        log.info("Not Modified");
                        return ;
                    } else {
                        throw e;
                    }
                } catch (SocketTimeoutException e){
                    log.error(e.getMessage());
                    if(tryTimesLeft == 0){
                        if(file.exists()){
                            log.info("failed, use the local file");
                            return ;
                        } else {
                            throw e;
                        }
                    }
                }
            }
            fos = new FileOutputStream(file);
            fos.write(response.bodyAsBytes());
            log.info("download success : " + savePath + "/" + fileName);
        } finally {
            if(fos!=null){
                fos.close();
            }
        }
    }


    public static void  downLoadFromUrl(String urlStr,String savePath) throws Exception{
        String fileName = urlStr.substring(urlStr.lastIndexOf("/") + 1);
        downLoadFromUrl(urlStr,savePath,fileName);
    }

    public static void enableTLSv2() throws Exception {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        SSLContext sc = SSLContext.getInstance("TLSv1.2");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

}

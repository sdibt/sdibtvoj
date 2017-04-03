package judge.remote.provider.sdut;




import judge.httpclient.HttpBodyValidator;
import judge.httpclient.HttpStatusValidator;
import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.SimpleHttpResponse;
import judge.httpclient.SimpleHttpResponseValidator;
import judge.httpclient.SimpleNameValueEntityFactory;
import judge.remote.RemoteOjInfo;
import judge.remote.account.RemoteAccount;
import judge.remote.loginer.RetentiveLoginer;

import org.apache.commons.lang3.Validate;
import org.apache.http.HttpEntity;
import org.springframework.stereotype.Component;

@Component
public class SDUTLoginer extends RetentiveLoginer {

    @Override
    public RemoteOjInfo getOjInfo() {
        return SDUTInfo.INFO;
    }
    @Override
    protected void loginEnforce(RemoteAccount account, DedicatedHttpClient client) {
        if (client.get("/onlinejudge2/index.php/Home/Login/").getBody().contains("logout")) {
            return;
        }

        HttpEntity entity = SimpleNameValueEntityFactory.create( //
                "user_name", account.getAccountId(), //
                "password", account.getPassword());
        
     
       client.post("/onlinejudge2/index.php/Home/Login/login", entity, new SimpleHttpResponseValidator() {
           @Override
            public void validate(SimpleHttpResponse response) throws Exception {
                Validate.isTrue(response.getBody().contains("javascript:history.back(-1)"));
            }
        });
        
    }

}

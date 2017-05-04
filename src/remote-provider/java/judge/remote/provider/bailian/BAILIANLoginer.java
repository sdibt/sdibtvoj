package judge.remote.provider.bailian;


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
public class BAILIANLoginer extends RetentiveLoginer {

    @Override
    public RemoteOjInfo getOjInfo() {
        return BAILIANInfo.INFO;
    }
    @Override
    protected void loginEnforce(RemoteAccount account, DedicatedHttpClient client) {
        if (client.get("/").getBody().contains("登出")) {
            return;
        }

        HttpEntity entity = SimpleNameValueEntityFactory.create( //
                "email", account.getAccountId(), //
                "password", account.getPassword());
        client.post("/api/auth/login/", entity, HttpStatusValidator.SC_OK);
        
    }

}

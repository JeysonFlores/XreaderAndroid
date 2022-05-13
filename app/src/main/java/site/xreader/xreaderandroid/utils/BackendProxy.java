package site.xreader.xreaderandroid.utils;

import android.content.Context;
import android.widget.Toast;

import com.studioidan.httpagent.HttpAgent;
import com.studioidan.httpagent.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import site.xreader.xreaderandroid.callbacks.ErrorCallback;
import site.xreader.xreaderandroid.callbacks.SimpleCallback;

public class BackendProxy {

    private final String API_URL = "http://xreader.site/API";
    private String token;

    public BackendProxy () { }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void login(Context c, String username, String password, SimpleCallback cb, ErrorCallback ecb) throws Exception {
        RequestParams params = new RequestParams();

        params.setParam("name", username);
        params.setParam("password", password);

        String URL = UrlBuilder.build(API_URL + "/login", params);

        HttpAgent.get(URL)
                .goString(new StringCallback() {
                    @Override
                    protected void onDone(boolean success, String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (success) {
                                if (!jsonObject.isNull("token")) {
                                    setToken(jsonObject.getString("token"));
                                    cb.call();
                                } else {
                                    ecb.call(jsonObject.getString("error"));
                                }
                            } else {
                                ecb.call(getErrorMessage());
                            }
                        } catch (Exception e) {
                            ecb.call(e.toString());
                        }
                    }
                });
    }
}

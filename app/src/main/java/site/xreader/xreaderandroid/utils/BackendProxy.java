package site.xreader.xreaderandroid.utils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import site.xreader.xreaderandroid.callbacks.SimpleCallback;

public class BackendProxy {

    private final String API_URL = "http://xreader.site/API";
    private String token;
    private RequestQueue queue;

    public BackendProxy () {
        this.queue = Volley.newRequestQueue(null);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void login(String username, String password, SimpleCallback cb) throws SecurityException {

        String URL = API_URL + "/login";

        JsonObjectRequest loginRequest = new JsonObjectRequest (Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        try {
                            String token = response.getString("token");
                            setToken(token);
                            cb.call();
                        } catch (JSONException e) {
                            throw new SecurityException();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new SecurityException();
            }
        });
    }
}

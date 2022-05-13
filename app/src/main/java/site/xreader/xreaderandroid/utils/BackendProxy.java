package site.xreader.xreaderandroid.utils;

import android.content.Context;

import com.studioidan.httpagent.HttpAgent;
import com.studioidan.httpagent.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import site.xreader.xreaderandroid.callbacks.ErrorCallback;
import site.xreader.xreaderandroid.callbacks.ListNovelCallback;
import site.xreader.xreaderandroid.callbacks.SimpleCallback;
import site.xreader.xreaderandroid.models.Novel;

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

    public void login(String username, String password, SimpleCallback cb, ErrorCallback ecb) {
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

    public void getNovels(ListNovelCallback cb, ErrorCallback ecb) {
        RequestParams params = new RequestParams();
        params.setParam("token", getToken());

        String URL = UrlBuilder.build(API_URL + "/novels", params);

        HttpAgent.get(URL)
                .goString(new StringCallback() {
                    @Override
                    protected void onDone(boolean success, String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (success) {
                                if (!jsonObject.isNull("novels")) {
                                    ArrayList<Novel> responseNovels = new ArrayList<>();
                                    JSONArray responseArray = jsonObject.getJSONArray("novels");

                                    for (int i=0; i < responseArray.length(); i++){
                                        JSONObject element = responseArray.getJSONObject(i);

                                        int id = element.getInt("id");
                                        String name = element.getString("name");
                                        String description = element.getString("description");
                                        String author = element.getString("author");
                                        String image_path = element.getString("image_path");
                                        int publishing_year = element.getInt("publishing_year");

                                        responseNovels.add(new Novel(id, name, description, author, image_path, publishing_year));
                                    }

                                    cb.call(responseNovels);
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

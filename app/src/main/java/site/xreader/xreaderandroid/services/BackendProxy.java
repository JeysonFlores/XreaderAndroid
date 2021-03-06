package site.xreader.xreaderandroid.services;

import android.content.Context;

import com.studioidan.httpagent.HttpAgent;
import com.studioidan.httpagent.JsonCallback;
import com.studioidan.httpagent.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import site.xreader.xreaderandroid.callbacks.ErrorCallback;
import site.xreader.xreaderandroid.callbacks.ListNovelCallback;
import site.xreader.xreaderandroid.callbacks.ListVolumesCallback;
import site.xreader.xreaderandroid.callbacks.NovelCallback;
import site.xreader.xreaderandroid.callbacks.SimpleCallback;
import site.xreader.xreaderandroid.models.Novel;
import site.xreader.xreaderandroid.models.Volume;
import site.xreader.xreaderandroid.utils.RequestParams;
import site.xreader.xreaderandroid.utils.UrlBuilder;

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
                                ecb.call("RequestError");
                            }
                        } catch (Exception e) {
                            ecb.call("ResponseError");
                        }
                    }
                });
    }

    public void signup(String name, String username, String password, SimpleCallback cb, ErrorCallback ecb) {
        JSONObject body = new JSONObject();

        try {
            body.put("name", name);
            body.put("username", username);
            body.put("password", password);
        } catch (JSONException e) {
            ecb.call("BodyBuildError");
            return;
        }

        String URL = API_URL + "/signup";

        HttpAgent.post(URL)
                .withBody(body.toString())
                .goJson(new JsonCallback() {
                    @Override
                    protected void onDone(boolean success, JSONObject jsonResults) {
                        try{
                            if(success) {
                                if(!jsonResults.isNull("message")) {
                                    cb.call();
                                } else {
                                    ecb.call(jsonResults.getString("error"));
                                }
                            } else {
                                ecb.call("RequestError");
                            }
                        } catch (JSONException e) {
                            ecb.call("ResponseError");
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

    public void getNovelById(int id, NovelCallback cb, ErrorCallback ecb) {
        RequestParams params = new RequestParams();
        params.setParam("token", getToken());

        String URL = UrlBuilder.build(API_URL + "/novels/" + id , params);

        HttpAgent.get(URL)
                .goJson(new JsonCallback() {
                    @Override
                    protected void onDone(boolean success, JSONObject jsonResults) {
                        try {
                            if(success) {
                                int id = jsonResults.getInt("id");
                                String name = jsonResults.getString("name");
                                String description = jsonResults.getString("description");
                                String author = jsonResults.getString("author");
                                String image_path = jsonResults.getString("image_path");
                                int publishing_year = jsonResults.getInt("publishing_year");

                                cb.call(new Novel(id, name, description, author, image_path, publishing_year));
                            } else {
                                ecb.call("RequestError");
                            }
                        } catch (Exception e) {
                            ecb.call("ResponseError");
                        }
                    }
                });
    }

    public void getRecentNovels(ListNovelCallback cb, ErrorCallback ecb) {
        RequestParams params = new RequestParams();
        params.setParam("token", getToken());

        String URL = UrlBuilder.build(API_URL + "/novels/recent", params);

        HttpAgent.get(URL)
                .goJson(new JsonCallback() {
                    @Override
                    protected void onDone(boolean success, JSONObject jsonResults) {
                        try {
                            if (success) {
                                if (!jsonResults.isNull("novels")) {
                                    ArrayList<Novel> responseNovels = new ArrayList<>();
                                    JSONArray responseArray = jsonResults.getJSONArray("novels");

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
                                    ecb.call("NotFoundError");
                                }
                            } else {
                                ecb.call("RequestError");
                            }
                        } catch (Exception e) {
                            ecb.call("ResponseError");
                        }
                    }
                });
    }

    public void searchNovels(String query, ListNovelCallback cb, ErrorCallback ecb) {
        RequestParams params = new RequestParams();
        params.setParam("query", query);
        params.setParam("token", getToken());

        String URL = UrlBuilder.build(API_URL + "/novels/search", params);

        HttpAgent.get(URL)
                .goJson(new JsonCallback() {
                    @Override
                    protected void onDone(boolean success, JSONObject jsonResults) {
                        try {
                            if (success) {
                                if (!jsonResults.isNull("novels")) {
                                    ArrayList<Novel> responseNovels = new ArrayList<>();
                                    JSONArray responseArray = jsonResults.getJSONArray("novels");

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
                                    ecb.call("NotFoundError");
                                }
                            } else {
                                ecb.call("RequestError");
                            }
                        } catch (Exception e) {
                            ecb.call("ResponseError");
                        }
                    }
                });
    }

    public void getAllVolumesFromNovel(int novel_id, ListVolumesCallback cb, ErrorCallback ecb) {
        RequestParams params = new RequestParams();
        params.setParam("token", getToken());

        String URL = UrlBuilder.build(API_URL + "/novels/" + novel_id + "/volumes", params);

        HttpAgent.get(URL)
                .goJson(new JsonCallback() {
                    @Override
                    protected void onDone(boolean success, JSONObject jsonResults) {
                        try {
                            if(success) {
                                if(!jsonResults.isNull("volumes")) {
                                    ArrayList<Volume> responseVolumes = new ArrayList<>();
                                    JSONArray responseArray = jsonResults.getJSONArray("volumes");

                                    for(int i = 0; i < responseArray.length(); i++) {
                                        JSONObject element = responseArray.getJSONObject(i);

                                        int id = element.getInt("id");
                                        String name = element.getString("name");
                                        String image_path = element.getString("image_path");
                                        String link = element.getString("link");

                                        responseVolumes.add(new Volume(id, name, link, image_path));
                                    }

                                    cb.call(responseVolumes);
                                } else {
                                    ecb.call("NotFoundError");
                                }
                            } else {
                                ecb.call("RequestError");
                            }
                        } catch (Exception e) {
                            ecb.call("ResponseError");
                        }
                    }
                });
    }
}

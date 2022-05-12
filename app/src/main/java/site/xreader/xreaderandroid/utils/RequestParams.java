package site.xreader.xreaderandroid.utils;

import java.util.HashMap;
import java.util.Map;

public class RequestParams {

    private HashMap<String, String> data;

    public RequestParams() {
        this.data = new HashMap<String, String>();
    }

    public void setParam(String key, String value) {
        this.data.put(key, value);
    }

    public String toString() {
        String result = "?";

        for(Map.Entry<String, String> row : this.data.entrySet()) {
            result += row.getKey() + "=" + row.getValue() + "&";
        }

        return result.substring(0, result.length()-1);
    }
}

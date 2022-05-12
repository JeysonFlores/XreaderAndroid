package site.xreader.xreaderandroid.utils;

public class UrlBuilder {
    public static String build(String url, RequestParams params) {
        return url + params.toString();
    }
}

package ca.mcgill.ecse321.tutoringservice;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpsUtils {

    public static final String DEFAULT_BASE_URL = "https://project-backend-10.herokuapp.com";

    private static String baseUrl;
    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        baseUrl = DEFAULT_BASE_URL;
    }

    /**
     * @return
     */
    public static String getBaseUrl() {
        return baseUrl;
    }

    /**
     * @param baseUrl
     */
    public static void setBaseUrl(String baseUrl) {
        HttpsUtils.baseUrl = baseUrl;
    }

    /**
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    /**
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    /**
     * @param relativeUrl
     * @return
     */
    private static String getAbsoluteUrl(String relativeUrl) {
        return baseUrl + relativeUrl;
    }
}

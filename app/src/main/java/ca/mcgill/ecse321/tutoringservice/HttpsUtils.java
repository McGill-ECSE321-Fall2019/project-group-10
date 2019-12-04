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
     * Return a value that represent the Base URL of the Application.
     * This method allows the URL to be returned for backend calls, and allows
     * the successful use of backend calls.  
     * @return A String containing the address of the URL that forms the base 
     *         call to the backend.
     */
    public static String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Update the base URL to a different URL. This base URL will form the new 
     * starting point for all calls to the backend through the UI. 
     * @param baseUrl A String that contains the value to update the base to.
     */
    public static void setBaseUrl(String baseUrl) {
        HttpsUtils.baseUrl = baseUrl;
    }

    /**
     * This method handles a Get request made in HTTP protocol to the backend database.
     * Note that this method will handle the parsing of the response, as well as all needed processing 
     * of the request, through its parameters. 
     * @param url             The URL for the Get request to be accessed at. Note that this is a relative
     *                        URL, which can be viewed as the endpoint of a request to add to the base URL.
     * @param params          The parameters that the get request requires in order to execute
     *                        the HTTP protocol. 
     * @param responseHandler An object definition that defines the response behavior the specific request.
     *                        This response handler will define the behavior both in the success case and the
     *                        error case. This behavior includes the parsing of the response by the handler.  
     */
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        //Absolute URL is used, the parameter is the relative URL
    	client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * This method handles a Post request made in HTTP protocol to the backend database.
     * Note that this method will handle the parsing of the response, as well as all needed processing 
     * of the request, through its parameters. 
     * @param url             The URL for the Post request to be accessed at. Note that this is a relative
     *                        URL, which can be viewed as the endpoint of a request to add to the base URL.
     * @param params          The parameters that the get request requires in order to execute
     *                        the HTTP protocol. 
     * @param responseHandler An object definition that defines the response behavior the specific request.
     *                        This response handler will define the behavior both in the success case and the
     *                        error case. This behavior includes the parsing of the response by the handler.
     */
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
    	//Absolute URL is used, the parameter is the relative URL
    	client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * This method handles a Get request made in HTTP protocol to the backend database.
     * Note that this method will handle the parsing of the response, as well as all needed processing 
     * of the request, through its parameters. 
     * @param url             The URL for the Get request to be accessed at. Note that this is the full
     *                        URL, which can be used to execute the Get request.
     * @param params          The parameters that the get request requires in order to execute
     *                        the HTTP protocol. 
     * @param responseHandler An object definition that defines the response behavior the specific request.
     *                        This response handler will define the behavior both in the success case and the
     *                        error case. This behavior includes the parsing of the response by the handler.
     */
    public static void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    /**
     * This method handles a Post request made in HTTP protocol to the backend database.
     * Note that this method will handle the parsing of the response, as well as all needed processing 
     * of the request, through its parameters. 
     * @param url             The URL for the Post request to be accessed at. Note that this is the full
     *                        URL, which can be used to execute the Get request.
     * @param params          The parameters that the get request requires in order to execute
     *                        the HTTP protocol. 
     * @param responseHandler An object definition that defines the response behavior the specific request.
     *                        This response handler will define the behavior both in the success case and the
     *                        error case. This behavior includes the parsing of the response by the handler.
     */
    public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    /**
     * This method concatenates the base URL with the relative URL to return the full URL of a request.
     * Specifically, this method is used to create a new request. Note that the Base URL is saved as a 
     * class variable.
     * @param relativeUrl The URL for a specific HTTP request that represents the endpoint of that
     *                    request. This relative URL is relative to the current Base URL.
     * @return            The entire (or absolute) URL of a specific request. This URL is the 
     *                    one to be used when executing a particular request. 
     */
    private static String getAbsoluteUrl(String relativeUrl) {
        return baseUrl + relativeUrl;
    }
}

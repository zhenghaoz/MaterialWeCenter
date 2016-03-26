package com.zjut.material_wecenter;

import android.content.SharedPreferences;
import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/3/19.
 */
public class ClientYC{


    private final static String URL_LOGIN = "http://www.ycjw.zjut.edu.cn//logon.aspx";
    private final static String URL_QUERY="http://www.ycjw.zjut.edu.cn//stdgl/stdgl_index.aspx";
    private final static String URL_COURSES = "http://www.ycjw.zjut.edu.cn//stdgl/cxxt/Web_Std_XQKB.aspx";
    private final static String URL_REPORT = "http://www.ycjw.zjut.edu.cn//stdgl/cxxt/cjcx/Cjcx_Xsgrcj.aspx";
    private final static String URL_RESULT="http://www.ycjw.zjut.edu.cn//stdgl/cxxt/webpkjgcx.aspx";
    private final static String URL_POINT="http://www.ycjw.zjut.edu.cn//stdgl/cxxt/byshmx.aspx";
    private static AsyncHttpClient client;
    public final static int REPORT = 0;
    public final static int COURSES = 1;
    public final static int PLAN = 2;
    public final static int RESULT = 3;
    public final static int POINT = 4;
    public static String viewState;
    public static String username;
    public static String password;

    static {

        Log.e("static", "static");
        client=new AsyncHttpClient();
        client.setTimeout(10000);
        client.setEnableRedirects(true);
        client.setURLEncodingEnabled(true);
        client.addHeader("Host", "www.ycjw.zjut.edu.cn");
        client.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        client.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
    }

    public static AsyncHttpClient getClient(){

        if(client==null){
            Log.e("static", "static");
            client=new AsyncHttpClient();
            client.setTimeout(10000);
            client.setEnableRedirects(true);
            client.setURLEncodingEnabled(true);
            client.addHeader("Host", "www.ycjw.zjut.edu.cn");
            client.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            client.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
        }
        return client;
    }

    private static RequestParams getLoginParams(){
        RequestParams params = new RequestParams();
        params.add("__EVENTTARGET", "");
        params.add("__EVENTARGUMENT", "");
        params.add("__VIEWSTATE", "dDwtMTU2MDM2OTk5Nzt0PDtsPGk8MT47PjtsPHQ8O2w8aTwzPjtpPDEzPjs+O2w8dDw7bDxpPDE+O2k8Mz47aTw1PjtpPDc+O2k8OT47aTwxMT47aTwxMz47aTwxNT47aTwxNz47PjtsPHQ8cDxwPGw8QmFja0ltYWdlVXJsOz47bDxodHRwOi8vd3d3Lnljancuemp1dC5lZHUuY24vL2ltYWdlcy9iZy5naWY7Pj47Pjs7Pjt0PHA8cDxsPEJhY2tJbWFnZVVybDs+O2w8aHR0cDovL3d3dy55Y2p3LnpqdXQuZWR1LmNuLy9pbWFnZXMvYmcxLmdpZjs+Pjs+Ozs+O3Q8cDxwPGw8QmFja0ltYWdlVXJsOz47bDxodHRwOi8vd3d3Lnljancuemp1dC5lZHUuY24vL2ltYWdlcy9iZzEuZ2lmOz4+Oz47Oz47dDxwPHA8bDxCYWNrSW1hZ2VVcmw7PjtsPGh0dHA6Ly93d3cueWNqdy56anV0LmVkdS5jbi8vaW1hZ2VzL2JnMS5naWY7Pj47Pjs7Pjt0PHA8cDxsPEJhY2tJbWFnZVVybDs+O2w8aHR0cDovL3d3dy55Y2p3LnpqdXQuZWR1LmNuLy9pbWFnZXMvYmcxLmdpZjs+Pjs+Ozs+O3Q8cDxwPGw8QmFja0ltYWdlVXJsOz47bDxodHRwOi8vd3d3Lnljancuemp1dC5lZHUuY24vL2ltYWdlcy9iZzEuZ2lmOz4+Oz47Oz47dDxwPHA8bDxCYWNrSW1hZ2VVcmw7PjtsPGh0dHA6Ly93d3cueWNqdy56anV0LmVkdS5jbi8vaW1hZ2VzL2JnMS5naWY7Pj47Pjs7Pjt0PHA8cDxsPEJhY2tJbWFnZVVybDs+O2w8aHR0cDovL3d3dy55Y2p3LnpqdXQuZWR1LmNuLy9pbWFnZXMvYmcxLmdpZjs+Pjs+Ozs+O3Q8cDxwPGw8QmFja0ltYWdlVXJsOz47bDxodHRwOi8vd3d3Lnljancuemp1dC5lZHUuY24vL2ltYWdlcy9iZzEuZ2lmOz4+Oz47Oz47Pj47dDx0PDt0PGk8Mz47QDwtLeeUqOaIt+exu+Weiy0tO+aVmeW4iDvlrabnlJ87PjtAPC0t55So5oi357G75Z6LLS075pWZ5biIO+WtpueUnzs+Pjs+Ozs+Oz4+Oz4+O2w8SW1nX0RMOz4+qmizg8nuU1ebhUFzNA/qu71sECk=");
        params.add("Cbo_LX", "学生");
        params.add("Txt_UserName", username);
        params.add("Txt_Password", password);
        params.add("Img_DL.x", "19");
        params.add("Img_DL.y", "4");
        return params;
    }

    private static RequestParams getQueryParams(){
        RequestParams params = new RequestParams();
        params.add("__EVENTTARGET", "LB_cxxt");
        params.add("__EVENTARGUMENT", "");
        params.add("__VIEWSTATE", viewState);
        params.add("Header1:TextBox1", "");
        return params;
    }

    private static RequestParams getPlanParams(){
        RequestParams params = new RequestParams();
        params.add("__EVENTTARGET", "Header3:Lb_title");
        params.add("__EVENTARGUMENT", "");
        params.add("__VIEWSTATE", viewState);
        params.add("Header1:TextBox1", "");
        params.add("Header2:TextBox1", "");
        params.add("Header3:TextBox1", "");
        params.add("HEADER4:TextBox1", "");
        params.add("HEADER5:TextBox1", "");
        return params;
    }

    private static RequestParams getPointParams(){
        RequestParams params = new RequestParams();
        params.add("__EVENTTARGET", "HEADER5:Lb_title");
        params.add("__EVENTARGUMENT", "");
        params.add("__VIEWSTATE", viewState);
        params.add("Header1:TextBox1", "");
        params.add("Header2:TextBox1", "");
        params.add("Header3:TextBox1", "");
        params.add("HEADER4:TextBox1", "");
        params.add("HEADER5:TextBox1", "");
        return params;
    }

    private static RequestParams getResultsParams(){
        RequestParams params = new RequestParams();
        params.add("__EVENTTARGET", "HEADER4:Lb_title");
        params.add("__EVENTARGUMENT", "");
        params.add("__VIEWSTATE", viewState);
        params.add("Header1:TextBox1", "");
        params.add("Header2:TextBox1", "");
        params.add("Header3:TextBox1", "");
        params.add("HEADER4:TextBox1", "");
        params.add("HEADER5:TextBox1", "");
        return params;
    }

    private static void setViewState(byte[] responseBody){
        String html = null;

        try {
            html = new String(responseBody, "gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Document doc = Jsoup.parse(html);
        viewState = doc.select("input[name=__VIEWSTATE]").val();
    }


    public static void plan(final String term, final AsyncHttpResponseHandler handler){

        client.addHeader("Referer", URL_LOGIN);

        client.post(URL_LOGIN, getLoginParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("login", "Success" + statusCode);
                setViewState(responseBody);

                client.addHeader("Referer", URL_QUERY);
                client.post(URL_QUERY, getQueryParams(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("query", "Success" + statusCode);
                        setViewState(responseBody);

                        client.addHeader("Referer", URL_QUERY);
                        client.post(URL_QUERY, getPlanParams(),handler);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    public static void result(final String term, final AsyncHttpResponseHandler handler){

        client.addHeader("Referer", URL_LOGIN);

        client.post(URL_LOGIN, getLoginParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("login", "Success" + statusCode);
                setViewState(responseBody);

                client.addHeader("Referer", URL_QUERY);
                client.post(URL_QUERY, getQueryParams(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("query", "Success" + statusCode);
                        setViewState(responseBody);

                        client.addHeader("Referer", URL_QUERY);
                        client.post(URL_QUERY, getResultsParams(), new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Log.e("results", "Success" + statusCode);

                                setViewState(responseBody);

                                RequestParams params = new RequestParams();
                                params.add("__EVENTTARGET", "");
                                params.add("__EVENTARGUMENT", "");
                                params.add("__VIEWSTATE", viewState);
                                params.add("Cbo_Xueqi", term);
                                params.add("Button1", "");

                                client.addHeader("Referer", URL_RESULT);
                                client.post(URL_RESULT, params, handler);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    public static void point(final String term, final AsyncHttpResponseHandler handler){

        client.addHeader("Referer", URL_LOGIN);

        client.post(URL_LOGIN, getLoginParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("login", "Success" + statusCode);
                setViewState(responseBody);

                client.addHeader("Referer", URL_QUERY);
                client.post(URL_QUERY, getQueryParams(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("query", "Success" + statusCode);
                        setViewState(responseBody);

                        client.addHeader("Referer", URL_QUERY);
                        client.post(URL_QUERY, getPointParams(), handler);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    /**
     * doGet 发送GET请求
     * @param Url 请求的URL地址
     * @return 字符串（如果发生错误，那么返回NULL）
     */
    public static String doGet(String Url) {
        try {
            URL url = new URL(Url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStreamReader input = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(input);
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                builder.append(line);
            return builder.toString();
        } catch (IOException e) {
            return null;
        }
    }
}

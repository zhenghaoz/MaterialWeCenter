package com.sine_x.material_wecenter.controller.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sine_x.material_wecenter.ClientYC;
import com.sine_x.material_wecenter.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class YCJWActivity extends AppCompatActivity {

    private WebView content;
    private AsyncHttpResponseHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ycjw);
        initView();
        initType();
    }
    private void initView(){

        content=(WebView) findViewById(R.id.webView);
        content.setVisibility(WebView.VISIBLE);
        content.setInitialScale(240);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ycjw);
        setSupportActionBar(toolbar);
        setTitle("原创查询");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

    }

    private void initType(){
        Intent intent=getIntent();
        String value=intent.getStringExtra("value");
        Log.e("value", value);
        switch (intent.getIntExtra("type",5)){
            case ClientYC.PLAN:
                handler=new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("get","Success"+statusCode);
                        String s= null;
                        try {
                            s = new String(responseBody,"gb2312");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Document doc= Jsoup.parse(s);
                        String html=doc.getElementById("DG_GetGrjh").outerHtml();
                        content.loadDataWithBaseURL(null,html,"text/html", "utf-8",null);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                };
                ClientYC.plan( value, handler);
                break;
            case ClientYC.RESULT:
                handler=new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("get","Success"+statusCode);
                        String s= null;
                        try {
                            s = new String(responseBody,"gb2312");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Document doc= Jsoup.parse(s);
                        String html=doc.getElementById("DG_PTHasselect").outerHtml();
                        content.loadDataWithBaseURL(null,html,"text/html", "utf-8",null);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                };
                ClientYC.result(value, handler);
                break;
            case ClientYC.POINT:
                handler=new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("get","Success"+statusCode);
                        String s= null;
                        try {
                            s = new String(responseBody,"gb2312");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Document doc= Jsoup.parse(s);
                        String html=doc.getElementById("DG_GetGrjh").outerHtml();
                        content.loadDataWithBaseURL(null,html,"text/html", "utf-8",null);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                };
                ClientYC.point(value,handler);
                break;
            default:
                break;
        }

    }
}

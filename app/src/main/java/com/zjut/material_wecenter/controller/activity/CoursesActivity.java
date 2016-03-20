package com.zjut.material_wecenter.controller.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zjut.material_wecenter.ClientYC;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.controller.adapter.ScoresAdapter;
import com.zjut.material_wecenter.models.Courses;
import com.zjut.material_wecenter.models.Score;

import org.json.JSONException;
import org.json.JSONObject;

public class CoursesActivity extends AppCompatActivity {

    private Gson gson;
    private Courses courses;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        initCourseView();

    }

    private void initCourseView(){

        Intent intent=getIntent();
        value=intent.getStringExtra("value");

        new LoadCourses().execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_courses);
        setSupportActionBar(toolbar);
        setTitle("课表查询");
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

    private TextView getCourseView(){

        View v= LayoutInflater.from(this).inflate(R.layout.item_courses,null);
        return (TextView)v.findViewById(R.id.textView_courses);

    }

    private int dip2px( float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int getWidthUnit(){
        return (this.getWindowManager().getDefaultDisplay().getWidth()-dip2px(30))/7;
    }

    private int getViewWithUnit(){
        return (this.getWindowManager().getDefaultDisplay().getWidth()-dip2px(38))/7;
    }
    private void setCourses(Courses courses){

        RelativeLayout rl=(RelativeLayout)findViewById(R.id.relativeLayout);

        int n=courses.getMsg().size();

        for(int i=0;i<n;i++){
            String name=courses.getMsg().get(i).getName();
            String classInfo=courses.getMsg().get(i).getClassinfo();
            if(!classInfo.equals(" ")){

                String lefts[]=classInfo.split("\\(");

                for(int k=0;k<lefts.length-1;k++){

                    TextView tv=getCourseView();
                    int week=Integer.parseInt(lefts[k].substring(lefts[k].length() - 1));
                    int rightIndex = lefts[k + 1].indexOf(")");
                    String s[]=lefts[k+1].substring(0,rightIndex).split("-");
                    int classNum=s.length;
                    int start=Integer.parseInt(s[0]);
                    RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(getViewWithUnit(),dip2px(49)*classNum);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    lp.leftMargin=getWidthUnit()*(week%7);
                    lp.topMargin=dip2px(50)*(start-1);
                    tv.setLayoutParams(lp);
                    tv.setBackgroundResource(R.drawable.stroker);
                    tv.setText(name);
                    rl.addView(tv);

                }

            }
        }
    }

    private class LoadCourses extends AsyncTask<Integer,Integer,Integer> {

        String json;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {


            json= ClientYC.doGet("http://api.zjut.com/student/class.php?username="+ClientYC.username+
                            "&password="+ClientYC.password+"&term="+value);
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            String status= null;
            try {
                JSONObject object=new JSONObject(json);
                status = object.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(status.equals("success")){
                gson=new Gson();
                courses=gson.fromJson(json,Courses.class);
                setCourses(courses);
            }
            else {
                Toast.makeText(CoursesActivity.this, "帐号、密码或学期错误", Toast.LENGTH_LONG).show();
            }
        }
    }
}

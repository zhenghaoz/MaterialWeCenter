package com.zjut.material_wecenter.controller.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zjut.material_wecenter.ClientYC;
import com.zjut.material_wecenter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CardActivity extends AppCompatActivity {

    private TextView name;
    private TextView balance;
    private LinearLayout bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        initView();

        new LoadCard().execute();
    }
    private void initView(){
        name=(TextView)findViewById(R.id.textView_cardName);
        balance=(TextView)findViewById(R.id.textView_cardBalance);
        bills=(LinearLayout)findViewById(R.id.linear_bills);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_card);
        setSupportActionBar(toolbar);
        setTitle("校园卡查询");
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

    private class LoadCard extends AsyncTask<Integer,Integer,Integer> {

        String json;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {

            Log.e("Load", "Starting");
            json= ClientYC.doGet("http://api.zjut.com/student/cardRecords.php?username="+ClientYC.username+
                    "&password="+ClientYC.username.substring(6));
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            setView(json);
        }
    }

    private void setView(String json){

        try {
            JSONObject jsonObject=new JSONObject(json);
            String status=jsonObject.getString("status");
            if(status.equals("success")){
                JSONObject msg=jsonObject.getJSONObject("msg");
                JSONObject balanceJson=msg.getJSONObject("余额");
                String name=balanceJson.getString("姓名");
                String balance=balanceJson.getString("卡余额");

                this.name.setText(name);
                this.balance.setText(balance);


                JSONObject bill=msg.getJSONObject("今日账单");
                JSONArray bills=bill.getJSONArray("msg");
                int n=bill.getInt("num");
                for(int i=0;i<n;i++){
                    JSONObject object=bills.getJSONObject(i);
                    String palace=object.getString("站点");
                    String amount=object.getString("交易额");
                    String time=object.getString("到账时间");

                    View v=new ViewHolder(this,palace,time,amount).getView();

                    this.bills.addView(v);

                }

            }
            else {
                Toast.makeText(CardActivity.this, "帐号、密码或学期错误", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class ViewHolder{
        LinearLayout bill;
        TextView palace;
        TextView time;
        TextView amount;
        public ViewHolder(Context context,String palace,String time,String amount){
            View v= LayoutInflater.from(context).inflate(R.layout.item_card,null);
            this.bill=(LinearLayout) v.findViewById(R.id.linear_bill);
            this.palace=(TextView)v.findViewById(R.id.textView_cardPalace);
            this.time=(TextView)v.findViewById(R.id.textView_cardTime);
            this.amount=(TextView)v.findViewById(R.id.textView_cardAmount);

            this.palace.setText(palace);
            this.time.setText(time);
            this.amount.setText(amount);
        }

        public View getView(){
            return bill;
        }
    }
}

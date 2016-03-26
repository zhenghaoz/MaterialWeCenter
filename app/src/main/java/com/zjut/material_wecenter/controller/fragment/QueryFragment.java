package com.zjut.material_wecenter.controller.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zjut.material_wecenter.ClientYC;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.controller.activity.BindActivity;
import com.zjut.material_wecenter.controller.activity.CardActivity;
import com.zjut.material_wecenter.controller.activity.CoursesActivity;
import com.zjut.material_wecenter.controller.activity.ScoreActivity;
import com.zjut.material_wecenter.controller.activity.YCJWActivity;

/**
 * Created by Administrator on 2016/3/19.
 */
public class QueryFragment extends Fragment implements View.OnClickListener{

    private TextView user;
    private TextView courses;
    private TextView scores;
    private TextView plan;
    private TextView examination;
    private TextView point;
    private TextView card;
    private Spinner term;

    private boolean isBind;
    private String username;
    private String password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_query, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        getUserInfo();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==1){
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
            String username = sharedPreferences.getString("username", null);
            user.setText(username);
            isBind=true;
            Toast.makeText(getActivity(), "绑定成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

        String value=(String)term.getSelectedItem();
        Intent intent;
        if(v.getId()==R.id.textView_user_query){
            intent=new Intent(getActivity(),BindActivity.class);
            startActivityForResult(intent, 0);
        }
        else if(isBind){
            switch (v.getId()) {
                case R.id.textView_courses:
                    intent=new Intent(getActivity(), CoursesActivity.class);
                    intent.putExtra("value",value);
                    startActivity(intent);
                    break;
                case R.id.textView_scores:
                    intent=new Intent(getActivity(),ScoreActivity.class);
                    intent.putExtra("value",value);
                    startActivity(intent);
                    break;
                case R.id.textView_plan:
                    intent=new Intent(getActivity(),YCJWActivity.class);
                    intent.putExtra("value",value);
                    intent.putExtra("type", ClientYC.PLAN);
                    startActivity(intent);
                    break;
                case R.id.textView_examination:
                    intent=new Intent(getActivity(),YCJWActivity.class);
                    intent.putExtra("value",value);
                    intent.putExtra("type",ClientYC.RESULT);
                    startActivity(intent);
                    break;
                case R.id.textView_point:
                    intent=new Intent(getActivity(),YCJWActivity.class);
                    intent.putExtra("value",value);
                    intent.putExtra("type",ClientYC.POINT);
                    startActivity(intent);
                    break;
                case R.id.textView_card:
                    intent=new Intent(getActivity(),CardActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
        else
            Toast.makeText(getActivity(), "请绑定帐号！", Toast.LENGTH_SHORT).show();


    }

    private void initView(View view){

        user=(TextView) view.findViewById(R.id.textView_user_query);
        courses=(TextView) view.findViewById(R.id.textView_courses);
        scores=(TextView) view.findViewById(R.id.textView_scores);
        plan=(TextView) view.findViewById(R.id.textView_plan);
        examination=(TextView) view.findViewById(R.id.textView_examination);
        point=(TextView) view.findViewById(R.id.textView_point);
        card=(TextView) view.findViewById(R.id.textView_card);
        term=(Spinner) view.findViewById(R.id.spinner_term);

        user.setOnClickListener(this);
        courses.setOnClickListener(this);
        scores.setOnClickListener(this);
        plan.setOnClickListener(this);
        examination.setOnClickListener(this);
        point.setOnClickListener(this);
        card.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.spin_term,
                R.layout.spinner);
        adapter.setDropDownViewResource(R.layout.spinner);
        term.setAdapter(adapter);

        term.setSelection(33);

    }

    private void getUserInfo(){

        SharedPreferences preferences = getActivity().getSharedPreferences("userInfo", getActivity().MODE_APPEND);

        username = preferences.getString("username", null);
        password = preferences.getString("password", null);

        if (username == null) {
            isBind=false;
            user.setText("还未绑定原创登录帐号");
        } else {
            isBind=true;
            user.setText(username);
            ClientYC.username=username;
            ClientYC.password=password;
        }

    }
}

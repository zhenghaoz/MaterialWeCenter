package com.sine_x.material_wecenter.controller.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.sine_x.material_wecenter.R;

/**
 * Created by Administrator on 2016/3/21.
 */
public class CourseDialog extends Dialog {

    private TextView info;

    public CourseDialog(Context context, int themeResId,String info,String color) {
        super(context, themeResId);
        setContentView(R.layout.dialog_course);
        this.info=(TextView) findViewById(R.id.textView_course);
        this.info.setBackgroundColor(Color.parseColor(color));
        this.info.setText(info);
    }

    public CourseDialog(Context context) {
        super(context);
    }

    public CourseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}

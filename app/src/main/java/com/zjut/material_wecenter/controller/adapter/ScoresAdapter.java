package com.zjut.material_wecenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.models.Score;

/**
 * Created by Administrator on 2016/3/20.
 */
public class ScoresAdapter extends RecyclerView.Adapter {

    private Score score;
    private Context context;

    public ScoresAdapter(Context context,Score score) {
        this.context=context;
        this.score=score;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_scores,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Score.MsgEntity msgEntity=score.getMsg().get(position);

        ViewHolder viewHolder=(ViewHolder) holder;
        viewHolder.name.setText(msgEntity.getName());
        viewHolder.prop.setText(msgEntity.getClassprop());
        viewHolder.hour.setText(msgEntity.getClasshuor());
        viewHolder.credit.setText(msgEntity.getClasscredit());
        viewHolder.score.setText(msgEntity.getClassscore());
    }

    @Override
    public int getItemCount() {
        return score.getMsg()==null?0:score.getMsg().size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView prop;
        private TextView hour;
        private TextView credit;
        private TextView score;

        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.textView_scoreName);
            prop=(TextView) itemView.findViewById(R.id.textView_scoreProp);
            hour=(TextView) itemView.findViewById(R.id.textView_scoreHour);
            credit=(TextView) itemView.findViewById(R.id.textView_scoreCredit);
            score=(TextView) itemView.findViewById(R.id.textView_score);
        }
    }

}


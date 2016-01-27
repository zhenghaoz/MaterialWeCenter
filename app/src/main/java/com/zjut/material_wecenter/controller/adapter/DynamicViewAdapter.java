package com.zjut.material_wecenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zjut.material_wecenter.R;
import com.zjut.material_wecenter.models.Dynamic;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by windness on 2016/1/27.
 */
public class DynamicViewAdapter extends RecyclerView.Adapter<DynamicViewAdapter.ViewHolder>{

    private ArrayList<Dynamic> mList;
    private Context mContext;

    public DynamicViewAdapter(Context context, ArrayList<Dynamic> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dynamic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Dynamic dynamic = mList.get(position);

        String avatarFile = dynamic.getUser_info().getAvatar_file();
        if (!avatarFile.isEmpty())
            Picasso.with(mContext)
                    .load(avatarFile)
                    .into(holder.avatarImg);

        holder.dynamicTitle.setText(dynamic.getQuestion_info().getQuestion_content());
        holder.dynamicInfo.setText(String.valueOf(dynamic.getQuestion_info().getAnswer_count()) + "个回答 • "
                + String.valueOf(dynamic.getQuestion_info().getAgree_count()) + "次赞同");
//        holder.dynamicTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView avatarImg;
        private TextView dynamicTitle;
        private TextView dynamicInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            avatarImg = (CircleImageView) itemView.findViewById(R.id.avatar_img);
            dynamicTitle = (TextView) itemView.findViewById(R.id.dynamic_title);
            dynamicInfo = (TextView) itemView.findViewById(R.id.dynamic_info);
        }
    }
}

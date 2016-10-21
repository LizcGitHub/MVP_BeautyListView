package com.example.administrator.mvp_beautylistview.homePage.mvp.view.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.mvp_beautylistview.R;
import com.example.administrator.mvp_beautylistview.homePage.mvp.model.entity.bean.ImageInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/12.
 */

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ImageInfo> mData;
    public MyAdapter(Context context, ArrayList<ImageInfo> data) {
        mContext = context;
        mData = data;
    }
    @Override
    public int getCount() {
        return mData.size();
    }
    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.id_iv);
            holder.ll = (LinearLayout) convertView.findViewById(R.id.id_ll);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        //图片比例自适应
        LinearLayout.LayoutParams linearParms = (LinearLayout.LayoutParams) holder.ll.getLayoutParams();
        linearParms.height = (mData.get(position).getPxScreenHeight());         //设置height 设置的是 px
        holder.ll.setLayoutParams(linearParms);                                 //用于自适应 不同 比例的 图片

        Picasso.with(mContext)
                .load(mData.get(position).getImage())
                .into(holder.imageView);
        return convertView;
    }
    class ViewHolder {
        LinearLayout ll;
        ImageView imageView;
    }
}

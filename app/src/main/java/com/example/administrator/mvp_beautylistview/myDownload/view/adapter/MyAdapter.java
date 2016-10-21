package com.example.administrator.mvp_beautylistview.myDownload.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.administrator.mvp_beautylistview.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/16.
 */

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mData;
    public MyAdapter(Context context, ArrayList<String> data) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.id_item_image);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        //TODO:这里被改成Picasso
        Picasso.with(mContext)
                .load(mData.get(position))
                .into(holder.iv);
        return convertView;
    }
    class ViewHolder {
        //图片
        ImageView iv;
    }
}

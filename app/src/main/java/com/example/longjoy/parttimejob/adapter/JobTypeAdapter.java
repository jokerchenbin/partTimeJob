package com.example.longjoy.parttimejob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.longjoy.parttimejob.R;

/**
 * 类描述：
 * 创建人：陈彬
 * 创建时间：2016/4/12 16:40
 */
public class JobTypeAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private String[] type_str;

    public JobTypeAdapter(Context context,String [] str) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.type_str = str;
    }

    @Override
    public int getCount() {
        return type_str.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.job_type_item,parent,false);
        TextView tv_type = (TextView) view.findViewById(R.id.job_type_item_tv_type);
        tv_type.setText(type_str[position]);
        return view;
    }
}

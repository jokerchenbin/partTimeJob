package com.example.longjoy.parttimejob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.bean.Province;

import java.util.ArrayList;

/**
 * 类描述：城市列表的适配器
 * 创建人：陈彬
 * 创建时间：2015/12/30 15:29
 */
public class CityListAdapter extends BaseAdapter {

    private ArrayList<Province> list = new ArrayList<>();
    private LayoutInflater inflater;

    public CityListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    /**
     * Created by 陈彬 on 2015/12/30  15:31
     * 方法描述: 添加数据源
     */
    public void addAll(ArrayList<Province> l){
        list.addAll(l);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Province getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.commom_list_item,parent,false);
        TextView text = (TextView) view.findViewById(R.id.commom_list_item_text);
        text.setText(list.get(position).getProvince_name());
        return view;
    }
}

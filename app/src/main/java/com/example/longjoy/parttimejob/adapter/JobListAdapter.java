package com.example.longjoy.parttimejob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.longjoy.parttimejob.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 类描述：
 * 创建人：陈彬
 * 创建时间：2016/1/5 13:47
 */
public class JobListAdapter extends BaseAdapter {

    private HashMap<Integer,String> map;
    private String[] list;
    private Context context;
    private LayoutInflater inflater;

    public JobListAdapter(Context context,String [] s) {
        this.context = context;
        list = s;
        map = new HashMap<>();
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.choose_job_item,parent,false);
        CheckBox box = (CheckBox) view.findViewById(R.id.choose_job_item_cb_box);
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    map.put(position,buttonView.getText().toString());
                }else {
                    map.remove(position);
                }
            }
        });
        box.setText(list[position]);
        return view;
    }

    /**
     * Created by 陈彬 on 2016/1/5  14:06
     * 方法描述: 得到选择的职位类型
     */
    public String getResultStr(){
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i<list.length;i++){
            if (map.containsKey(i)){
                sb.append(map.get(i));
                sb.append(",");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}

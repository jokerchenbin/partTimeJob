package com.example.longjoy.parttimejob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.bean.JobInfo;
import com.example.longjoy.parttimejob.common.FunctionUtils;

import java.util.List;

/**
 * 类描述：
 * 创建人：陈彬
 * 创建时间：2016/3/18 16:07
 */
public class JobInfoAdapter extends BaseAdapter {
    private Context context;
    private List<JobInfo> list;

    public JobInfoAdapter(Context context, List<JobInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.job_info_item, parent, false);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.job_info_item_name);
            holder.tv_tag6 = (TextView) convertView.findViewById(R.id.job_info_item_tag6);
            holder.tv_tag2 = (TextView) convertView.findViewById(R.id.job_info_item_tag2);
            holder.tv_tag3 = (TextView) convertView.findViewById(R.id.job_info_item_tag3);
            holder.tv_tag4 = (TextView) convertView.findViewById(R.id.job_info_item_tag4);
            holder.tv_tag5 = (TextView) convertView.findViewById(R.id.job_info_item_tag5);
            holder.tv_place = (TextView) convertView.findViewById(R.id.job_info_item_place);
            holder.tv_date = (TextView) convertView.findViewById(R.id.job_info_item_date);
            holder.tv_money = (TextView) convertView.findViewById(R.id.job_info_item_money);
            holder.iv_photo = (ImageView) convertView.findViewById(R.id.job_info_item_photo);
            holder.tv_state = (TextView) convertView.findViewById(R.id.job_int_item_tv_state);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        JobInfo info = list.get(position);
        holder.tv_name.setText(info.getName());
        holder.tv_place.setText(info.getPalce());
        holder.tv_date.setText(info.getDate());
        setTag(holder, info.getTag());
        holder.tv_money.setText(info.getMoney());
        FunctionUtils.setImage(context, holder.iv_photo, info.getType() + "");
        if (info.isChecked()) {
            holder.tv_state.setVisibility(View.GONE);
        }
        return convertView;
    }


    /**
     * 设置比标记
     *
     * @param holder
     * @param tag
     */
    private void setTag(ViewHolder holder, String tag) {
        int number = 0;
        String[] arr = tag.split(",");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("2") && number < 3) {
                holder.tv_tag2.setVisibility(View.VISIBLE);
                number++;
            }
            if (arr[i].equals("3") && number < 3) {
                holder.tv_tag3.setVisibility(View.VISIBLE);
                number++;
            }
            if (arr[i].equals("4") && number < 3) {
                holder.tv_tag4.setVisibility(View.VISIBLE);
                number++;
            }
            if (arr[i].equals("5") && number < 3) {
                holder.tv_tag5.setVisibility(View.VISIBLE);
                number++;
            }
            if (arr[i].equals("6") && number < 3) {
                holder.tv_tag6.setVisibility(View.VISIBLE);
                number++;
            }
        }
    }


    private class ViewHolder {
        TextView tv_name, tv_tag1, tv_tag2, tv_tag6, tv_tag3, tv_tag4, tv_tag5, tv_place, tv_date, tv_money;
        ImageView iv_photo;
        TextView tv_state;
    }
}

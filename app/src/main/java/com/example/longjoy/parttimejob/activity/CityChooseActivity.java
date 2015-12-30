package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.adapter.CityListAdapter;

/**
 * Created by 陈彬 on 2015/12/30  14:00
 * 方法描述:  选择城市界面
 */
public class CityChooseActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_topBar,tv_back;
    private String[] provincs;
    private ListView leftListView,rightListView;
    private Context context;
    private Activity activity;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choose);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        provincs = getResources().getStringArray(R.array.province);


        initViewIDs();
        changeTopBarState();
    }

    private void initViewIDs() {
        leftListView = (ListView) findViewById(R.id.activity_city_choose_lv_left);
        rightListView = (ListView) findViewById(R.id.activity_city_choose_lv_right);
        CityListAdapter cityListAdapter = new CityListAdapter(context);
        cityListAdapter.addAll(AppApplication.list);

        leftListView.setAdapter(cityListAdapter);
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (AppApplication.list.get(position).getCityList() == null){

                }else {
                    String [] cities = AppApplication.list.get(position).getCityList();
                    arrayAdapter = new ArrayAdapter<>(context,
                            R.layout.commom_list_item, R.id.commom_list_item_text
                            , cities);
                    rightListView.setAdapter(arrayAdapter);
                }

            }
        });
    }


    /**
     * Created by 陈彬 on 2015/12/30  9:59
     * 方法描述: 改变头部状态
     */
    private void changeTopBarState() {
        tv_topBar = (TextView) findViewById(R.id.top_bar_common_title);
        tv_topBar.setText("切换城市");
        tv_back = (TextView) findViewById(R.id.top_button_tim);
        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_button_tim://返回键
                activity.finish();
                break;
        }
    }
}

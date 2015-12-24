package com.example.longjoy.parttimejob;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.longjoy.parttimejob.fragment.HomePageFragment;
import com.example.longjoy.parttimejob.fragment.MyFragment;
import com.example.longjoy.parttimejob.fragment.PartTimeJobFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout main_fragment;
    private FragmentManager fm;
    private RadioButton rbtn_FirstPage,rbtn_PartTimeJob,rbtn_My;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewIds();
    }


    /**
     * 初始化View上的布局ID
     */
    private void initViewIds(){
        main_fragment = (LinearLayout)findViewById(R.id.activity_main_framelayout);
        /* 单选按钮 */
        rbtn_FirstPage = (RadioButton)findViewById(R.id.activity_main_rbtn_firstPage);
        rbtn_FirstPage.setOnClickListener(this);
        rbtn_PartTimeJob = (RadioButton)findViewById(R.id.activity_main_rbtn_partTimeJob);
        rbtn_PartTimeJob.setOnClickListener(this);
        rbtn_My = (RadioButton)findViewById(R.id.activity_main_rbtn_my);
        rbtn_My.setOnClickListener(this);

        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.activity_main_framelayout,new HomePageFragment()).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_main_rbtn_firstPage:
                HomePageFragment firstPage = new HomePageFragment();
                fm.beginTransaction().replace(R.id.activity_main_framelayout, firstPage).commit();
                break;
            case R.id.activity_main_rbtn_partTimeJob:
                PartTimeJobFragment job = new PartTimeJobFragment();
                fm.beginTransaction().replace(R.id.activity_main_framelayout, job).commit();
                break;
            case R.id.activity_main_rbtn_my:
                MyFragment myFragment = new MyFragment();
                fm.beginTransaction().replace(R.id.activity_main_framelayout,myFragment).commit();
                break;
        }
    }
}

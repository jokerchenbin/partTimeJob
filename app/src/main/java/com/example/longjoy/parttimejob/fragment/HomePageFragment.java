package com.example.longjoy.parttimejob.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.longjoy.parttimejob.R;

/**
 * Created by 陈彬 on 2015/12/30  16:40
 * 方法描述:  首页
 */
public class HomePageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.firstpage_fragment,container,false);
        return view;
    }
}

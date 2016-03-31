package com.example.longjoy.parttimejob.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.activity.JobDetailActivity;
import com.example.longjoy.parttimejob.activity.MainActivity;
import com.example.longjoy.parttimejob.adapter.ImageAdapter;
import com.example.longjoy.parttimejob.bean.AD;
import com.example.longjoy.parttimejob.common.FunctionUtils;
import com.example.longjoy.parttimejob.widget.AutoScrollViewPager;
import com.example.longjoy.parttimejob.adapter.JobInfoAdapter;
import com.example.longjoy.parttimejob.bean.JobInfo;
import com.example.longjoy.parttimejob.tools.ToastDiy;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 陈彬 on 2015/12/30  16:40
 * 方法描述:  首页
 */
public class HomePageFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private Button btn_add;
    private ListView list;
    private JobInfoAdapter mAdapter;
    private List<JobInfo> jobList;
    private View view;
    private AutoScrollViewPager viewPager;
    private ImageAdapter adapter;
    private Drawable[] drawables;
    private SliderLayout mSlider;
    private List<AD> adList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.firstpage_fragment, container, false);
        initView(view);
        getAd(getContext());//获取广告
        getData(getContext());//获取数据

        return view;
    }

    /**
     * Created by 陈彬 on 2016/3/31  15:02
     * 方法描述: 获取广告
     */
    private void getAd(Context context) {
        BmobQuery<AD> ad = new BmobQuery<>();
        ad.findObjects(context, new FindListener<AD>() {
            @Override
            public void onSuccess(List<AD> list) {
                adList = list;
                putIntoMap(adList);//将返回数据放进map
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void putIntoMap(List<AD> adList) {
        HashMap<String, String> url_maps = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            AD ad = adList.get(i);
            url_maps.put("测试" + i, "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        }
        showViewPager(url_maps);
    }

    private void showViewPager(HashMap<String, String> map) {
        /*HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal",R.mipmap.hannibal);
        file_maps.put("Big Bang Theory",R.mipmap.bigbang);
        file_maps.put("House of Cards",R.mipmap.house);
        file_maps.put("Game of Thrones", R.mipmap.game_of_thrones);*/

        for (String name : map.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description(name)
                    .image(map.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mSlider.addSlider(textSliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);
    }

    /**
     * Created by 陈彬 on 2016/3/18  16:03
     * 方法描述: 获取兼职信息数据
     */
    private void getData(final Context context) {
        FunctionUtils.showLoadingDialog(getActivity());
        BmobQuery<JobInfo> query = new BmobQuery<>();
        query.findObjects(context, new FindListener<JobInfo>() {
            @Override
            public void onSuccess(List<JobInfo> jobInfolist) {
                jobList = jobInfolist;
                mAdapter = new JobInfoAdapter(context, jobInfolist);
                list.setAdapter(mAdapter);
                FunctionUtils.dissmisLoadingDialog();
            }

            @Override
            public void onError(int i, String s) {
                ToastDiy.showShort(context, s);
                FunctionUtils.dissmisLoadingDialog();
            }
        });
    }

    private void initView(View view) {
        Drawable able = getResources().getDrawable(R.mipmap.banner_jiazaizhong);
        drawables = new Drawable[]{able, getResources().getDrawable(R.mipmap.wodeneiyebeijing), able, getResources().getDrawable(R.mipmap.wodeneiyebeijing)};
        list = (ListView) view.findViewById(R.id.firstpage_list);
        View v = View.inflate(getContext(), R.layout.head, null);
        mSlider = (SliderLayout) v.findViewById(R.id.slider);
        list.addHeaderView(v);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), JobDetailActivity.class);
                intent.putExtra("type", "home");
                intent.putExtra("data", jobList.get(position - 1));
                startActivity(intent);
            }
        });
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}

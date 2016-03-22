package com.example.longjoy.parttimejob.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 类描述：
 * 创建人：陈彬
 * 创建时间：2016/3/22 10:01
 */
public class ImageAdapter extends PagerAdapter {

    private Context context;
    private Drawable[] drawables;


    public ImageAdapter(Context context, Drawable[] id) {
        this.context = context;
        drawables = id;
    }

    @Override
    public int getCount() {
        return drawables.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    /**
     * 类似于BaseAdapger的getView方法 用了将数据设置给view 由于它最多就3个界面，不需要viewHolder
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = View.inflate(context, R.layout.viewpageritem, null);
        ImageView imag = (ImageView) v.findViewById(R.id.imag);
        imag.setBackgroundDrawable(drawables[position]);
        container.addView(v);// 一定不能少，将view加入到viewPager中
        return v;
    }

    /**
     * 销毁page position： 当前需要消耗第几个page object:当前需要消耗的page
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

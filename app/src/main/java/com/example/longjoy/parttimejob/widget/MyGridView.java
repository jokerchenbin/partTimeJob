package com.example.longjoy.parttimejob.widget;

import android.widget.GridView;

/**
 * 类描述：
 * 创建人：陈彬
 * 创建时间：2016/3/17 16:24
 */
public class MyGridView extends GridView {

    public MyGridView(android.content.Context context,
                      android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

package com.im.pluto.callmelove.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.im.pluto.callmelove.R;

import java.util.Random;

/**
 * Created by admin on 2016/5/18.
 */
public class LoveLayout extends RelativeLayout {

    /* todo  动态改变图片使用,修改可用*/
    private Drawable[] drawables = new Drawable[3];
    /*产生随机数*/
    private Random mRandom = new Random();

    private int mDrawableWidth;/*Drable的宽度*/
    private int mDrawableHeight;/*Drable的高度*/
    private LayoutParams mParams;


    public LoveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {

        drawables[0] = getResources().getDrawable(R.mipmap.ic_launcher);
        drawables[1] = getResources().getDrawable(R.mipmap.ic_launcher);
        drawables[2] = getResources().getDrawable(R.mipmap.ic_launcher);

        /*图片的宽高*/
        mDrawableWidth = drawables[0].getIntrinsicWidth();
        mDrawableHeight = drawables[0].getIntrinsicHeight();

        mParams = new LayoutParams(mDrawableWidth, mDrawableHeight);

    }

    /**
     * 添加控件
     */
    public void addLove() {
        ImageView iv = new ImageView(getContext());
        /*Random.nextInt()方法： Returns range [0, n) 返回半开区间[0,n)内的一个随机数*/
        iv.setImageDrawable(drawables[mRandom.nextInt(drawables.length)]);

//        RelativeLayout.LayoutParams mParams = new LayoutParams(mDrawableWidth, mDrawableHeight);

        /*设定水平居中， 居于父容器的下方*//*todo 此处可调整*/
        mParams.addRule(CENTER_HORIZONTAL);
        mParams.addRule(ALIGN_PARENT_BOTTOM);

        /*添加子空间到该容器*/
        addView(iv, mParams);


    }

}

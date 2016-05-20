package com.im.pluto.callmelove.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.im.pluto.callmelove.R;
import com.im.pluto.callmelove.widget.tool.BezierEvaluator;

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

    /*todo 此处的宽高，是LoveLayout的宽高*/
    private int mWidth;
    private int mHeight;

    /*todo 加速动画*/
    private Interpolator mAccelerate = new AccelerateInterpolator();/*加速*/
    private Interpolator mDecelerate = new DecelerateInterpolator();/*减速*/
    private Interpolator mAccelerateDecelerate = new AccelerateDecelerateInterpolator();/*先加速后减速*/
    private Interpolator[] mInterpolators;/*加速动画集合*/

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

        /*todo 初始化加速动画集合*/
        mInterpolators = new Interpolator[3];
        mInterpolators[0] = mAccelerate;
        mInterpolators[1] = mDecelerate;
        mInterpolators[2] = mAccelerateDecelerate;

        drawables[0] = getResources().getDrawable(R.mipmap.ic_launcher);
        drawables[1] = getResources().getDrawable(R.mipmap.ic_launcher);
        drawables[2] = getResources().getDrawable(R.mipmap.ic_launcher);

        /*若在此处创建 则为在构造方法里创建，此时LoveLayout还没有被测量，故应在onMeasure方法中调用*/
//        /*todo getMeasuredWidth()：【getMeasuredHeight】
//        @return The raw measured width of this view.
//        返回这个视图的原始测量宽度/高度*/
//        mWidth = getMeasuredWidth();/*需要注意的是：只有在该视图被创建出来之后才可以进行测量*/
//        mHeight = getMeasuredHeight();

        /*图片的宽高*/
        mDrawableWidth = drawables[0].getIntrinsicWidth();
        mDrawableHeight = drawables[0].getIntrinsicHeight();

        mParams = new LayoutParams(mDrawableWidth, mDrawableHeight);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /*todo getMeasuredWidth()：【getMeasuredHeight】
        @return The raw measured width of this view.
        返回这个视图的原始测量宽度/高度*/
        mWidth = getMeasuredWidth();/*需要注意的是：只有在该视图被创建出来之后才可以进行测量*/
        mHeight = getMeasuredHeight();
    }

    /**
     * 添加控件
     */
    public void addLove() {
        final ImageView iv = new ImageView(getContext());
        /*Random.nextInt()方法： Returns range [0, n) 返回半开区间[0,n)内的一个随机数*/
        iv.setImageDrawable(drawables[mRandom.nextInt(drawables.length)]);

//        RelativeLayout.LayoutParams mParams = new LayoutParams(mDrawableWidth, mDrawableHeight);

        /*设定水平居中， 居于父容器的下方*//*todo 此处可调整*/
        mParams.addRule(CENTER_HORIZONTAL);
        mParams.addRule(ALIGN_PARENT_BOTTOM);

        /*添加子空间到该容器*/
        addView(iv, mParams);


        /*todo  动画走起*/
        /*1. 缩放 2.平移 3.透明【渐隐】
        * 属性动画集合*/
        AnimatorSet mAnimatorSet = getAnimator(iv);
        /*todo 此处的属性动画集合mAnimatorSet必须有值【属性动画】，否则开启属性动画mAnimatorSet.start()时，会报空指针异常*/

        /*设置监听器*/
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {/*注释掉的部分  根据需求决定使用不使用*/
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                super.onAnimationCancel(animation);
//            }

            @Override/*动画结束时，remove掉该imageView*/
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(iv);
            }

//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                super.onAnimationRepeat(animation);
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//            }
//
//            @Override
//            public void onAnimationPause(Animator animation) {
//                super.onAnimationPause(animation);
//            }
//
//            @Override
//            public void onAnimationResume(Animator animation) {
//                super.onAnimationResume(animation);
//            }
        });


        /*开启属性动画*/
        mAnimatorSet.start();
    }

    /**
     * 获取属性动画集合
     *
     * @param iv 需要设置属性动画的控件
     * @return
     */
    private AnimatorSet getAnimator(ImageView iv) {/* todo 此处必须要传入一个需要设置属性动画的控件*/
        /*为一个控件设置动画属性  iv.setScaleX(0.4f); 其中0.4f 表示的是Float类型，值为0.4 */

        /* 1.&2. 缩放*/
        ObjectAnimator mScaleX = ObjectAnimator.ofFloat(iv, "scaleX", 0.4f, 1f);
        ObjectAnimator mScaleY = ObjectAnimator.ofFloat(iv, "scaleY", 0.4f, 1f);

        /*3.alpha 透明度*/
        ObjectAnimator mAlpha = ObjectAnimator.ofFloat(iv, "alpha", 0.4f, 1f);

        /*TODO 三个动画同时进行*/
        AnimatorSet enterSet = new AnimatorSet();/*创建一个动画集合*/
        enterSet.setDuration(500L);/*设置动画的持续时间为500毫秒*/
        enterSet.playTogether(mScaleX, mScaleY, mAlpha);/**/

        /*4.贝塞尔动画 evaluator估值器 todo*/
        ValueAnimator mBezier = getBezierAnimator(iv);

        /*综合所有的属性动画的集合*/
        AnimatorSet mAllAnimatorSet = new AnimatorSet();

        /*让所有的属性动画按顺序执行，即先enterSet,在实现贝塞尔曲线动画*/
        mAllAnimatorSet.playSequentially(enterSet, mBezier);
        /*设置属性动画集合的使用者，此处为传入的参数 ImageView iv*/
        /*todo 思考：将传入的参数设置为Object,是否能够适用于任何控件？*/

        mAllAnimatorSet.setInterpolator(mInterpolators[mRandom.nextInt(mInterpolators.length)]);

        mAllAnimatorSet.setTarget(iv);


        return mAllAnimatorSet;
    }

    /**
     * 获取贝塞尔曲线的动画
     *
     * @param iv
     * @return
     */
    private ValueAnimator getBezierAnimator(final ImageView iv) {

        /*贝塞尔曲线怎么控制坐标的？ 见百度图【三次方贝塞尔曲线】
        * point0起始点 point1第一个转点  point2第二个转点 point3终点*/
        /*todo 起始点的Y坐标是 mHeight-mDrawableHeight还是 mHeight-mDrawableHeight/2 */
        PointF pointF0 = new PointF((mWidth - mDrawableWidth) / 2, mHeight - mDrawableHeight);/*X的坐标需要减去iv宽度的一半*/
        PointF pointF1 = getTogglePointF(1);
        PointF pointF2 = getTogglePointF(2);
        PointF pointF3 = new PointF(mRandom.nextInt(mWidth), 0);/*顶端消失*/


        /*todo 属性动画不仅仅可以改变View的属性，还可以改变自定义的一些属性 如PointF*/
        /*point0动画的起始点，point3动画的结束点*/
        BezierEvaluator mBezierEvaluator = new BezierEvaluator(pointF1, pointF2);
        ValueAnimator mValueAnimator = ValueAnimator.ofObject(mBezierEvaluator, pointF0, pointF3);

        mValueAnimator.setDuration(3000L);/*设置动画时间为3000毫秒*/
        /*为Value动画添加数据改变的监听器 todo*/
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            /*该方法在动画的执行期间，此处为3000L毫秒中的任何一个时间都会不断的返回新的估值*/
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {/*todo iv变化检测*/
                /*作用：获取ValueAnimator动画的估值 todo
                 * Animated动画的 */
                /*todo Point：坐标【int类型】     PointF: float类型的坐标，*/
                PointF mPointF = (PointF) animation.getAnimatedValue();/*todo pointF是做什么用的？*/

                /*todo 控制属性的变化! 思考：为什么此处的iv要改为final类型呢？*/
                iv.setX(mPointF.x);/*这是设置iv的x坐标？ 由传入的参数猜测*/
                iv.setY(mPointF.y);/*这是设置iv的Y坐标？ 由传入的参数猜测*/
//                iv.setAlpha(mPointF.y/mHeight);/* 透明度要求1~0 透明度为1时完全透明*/
                iv.setAlpha(1 - animation.getAnimatedFraction());/*获取百分比0~1*/
            }
        });
        return mValueAnimator;
    }

    /**
     * 返回下半部分 或者 上半部分的坐标
     *
     * @param i 【i = 1:下半部分  i = 2: 上半部分】
     * @return
     */
    private PointF getTogglePointF(int i) {

        PointF mPointF = new PointF();
        /*设定X坐标*/
        mPointF.x = mRandom.nextInt(mWidth); /*X坐标 屏幕宽度范围内均可*/
        /*设定Y坐标*/
        if (1 == i) {/*下半部分，Y的范围 mHeight/2 ~ mHeight todo */
            mPointF.y = mRandom.nextInt(mHeight / 2) + mHeight / 2;
        } else {/*上半部分，Y的取值范围 0 ~ mHeight/2*/
            mPointF.y = mRandom.nextInt(mHeight / 2);
        }
        return mPointF;
    }

}

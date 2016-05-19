package com.im.pluto.callmelove.widget.tool;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by admin on 2016/5/19.
 */
public class BezierEvaluator implements TypeEvaluator<PointF> {

    private PointF pointF1;/*拐点1*/
    private PointF pointF2;/*拐点2*/

    /**
     * 构造方法，设置拐点坐标
     * @param pointF1 拐点1 下半部分
     * @param pointF2 拐点2 上半部分
     */
    public BezierEvaluator(PointF pointF1, PointF pointF2) {
        this.pointF1 = pointF1;
        this.pointF2 = pointF2;
    }

    /**
     * 估值
     * @param t 百分比：0~1.0f，在该Duration时间段内，任何时间点的完成度
     * @param pointF0 （动画）起始点的值
     * @param pointF3 （动画）结束点的值
     * @return 估值坐标【某一时间点时的具体坐标值？】
     */
    @Override
//    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {/*原本的参数*/
    public PointF evaluate(float t, PointF pointF0, PointF pointF3) {/*为方便使用，修改后*/

        /*创建要返回的坐标点*/
        PointF mPointF = new PointF();

        /*设置坐标点的横坐标*/
        mPointF.x = pointF0.x * (1-t)*(1-t)*(1-t)
                  + 3*pointF1.x * t *(1-t)*(1-t)
                  + 3*pointF2.x * t * t * (1-t)
                  + pointF3.x * t * t * t ;
        /*设置坐标点的纵坐标*/
        mPointF.y = pointF0.y * (1-t)*(1-t)*(1-t)
                + 3*pointF1.y * t *(1-t)*(1-t)
                + 3*pointF2.y * t * t * (1-t)
                + pointF3.y * t * t * t ;

        return mPointF;
    }
}

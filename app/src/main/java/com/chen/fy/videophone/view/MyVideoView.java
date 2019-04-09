package com.chen.fy.videophone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.VideoView;

/**
 * 自定义VideoView,实现播放器的大小设置
 */
public class MyVideoView extends VideoView{

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //把测量结果直接返回,此时为屏幕的大小
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    /**
     * 设置屏幕的大小
     * @param width   屏幕宽
     * @param height  屏幕高
     */
    public void setVideoSize(int width,int height){
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        setLayoutParams(layoutParams);
    }
}

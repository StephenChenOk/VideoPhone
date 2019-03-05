package com.chen.fy.videophone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.chen.fy.videophone.R;

public class BeginActivity extends AppCompatActivity{

    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_view);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //在主线程延迟2s进行
                startMainActivity();
            }
        },3000);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        startMainActivity();
        return super.onTouchEvent(event);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);  //移除消息,避免当一进入主界面就立刻退出时,延迟三秒打开主界面的消息还存在
    }
}

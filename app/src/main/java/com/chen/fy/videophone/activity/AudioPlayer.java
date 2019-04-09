package com.chen.fy.videophone.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.fy.videophone.IMediaService;
import com.chen.fy.videophone.R;
import com.chen.fy.videophone.service.MediaService;

public class AudioPlayer extends AppCompatActivity {


    private TextView tv_song;
    private TextView tv_singer;
    private ImageView iv_pre;
    private ImageView iv_start;
    private ImageView iv_next;

    /**
     * 当前播放的音频位置
     */
    private int position;

    /**
     * 服务的代理类,即MediaService的代理类
     */
    private IMediaService iMediaService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        /**
         * 当活动与服务连接成功时回调
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取MediaService服务的代理类,就可以在活动中操作服务了
            iMediaService = IMediaService.Stub.asInterface(service);

            try {
                iMediaService.openAudio(position);  //打开音乐
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        /**
         * 当活动与服务断开时回调这个方法
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMediaService = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_player);

        initView();

        getData();
        bindAndStartService();
    }

    private void initView() {
        tv_song = findViewById(R.id.song_name);
        tv_singer = findViewById(R.id.singer_name);
        iv_pre = findViewById(R.id.pre_audio);
        iv_start = findViewById(R.id.start_audio);
        iv_next = findViewById(R.id.next_audio);

        MyOnClickListener myOnClickListener = new MyOnClickListener(); //使用同一个对象,消耗资源少
        tv_song.setOnClickListener(myOnClickListener);
        tv_singer.setOnClickListener(myOnClickListener);
        iv_pre.setOnClickListener(myOnClickListener);
        iv_start.setOnClickListener(myOnClickListener);
        iv_next.setOnClickListener(myOnClickListener);
    }

    class MyOnClickListener implements View.OnClickListener{

        //用来实现播放与暂停按钮图片的切换
        private boolean isPlaying = true;

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.song_name:
                    break;
                case R.id.singer_name:
                    break;
                case R.id.pre_audio:
                    break;
                case R.id.start_audio:
                    isPlaying = !isPlaying;
                    if(isPlaying){
                        iv_start.setImageResource(R.drawable.pause_logo);
                    }else{
                        iv_start.setImageResource(R.drawable.start_logo);
                    }

                    //当音乐正在播放时,暂停
                    try {
                        if(iMediaService.isPlaying()){
                            iMediaService.pause();
                        }else{
                            iMediaService.start();
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    //当音乐暂停时,播放
                    break;
                case R.id.next_audio:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serviceConnection!=null){
            unbindService(serviceConnection);   //接触绑定
        }
    }

    private void bindAndStartService() {
        Intent intent = new Intent(this, MediaService.class);
        intent.setAction("com.chen.videoPhone_openMusic");
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
        //避免service被重新创建,因为bind绑定服务时每次都会重新实例化一个实例,而start则不会
        startService(intent);
    }

    private void getData() {
        //得到所传来的播放的音频位置
        position = getIntent().getIntExtra("position", 0);
    }
}

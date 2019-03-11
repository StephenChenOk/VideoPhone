package com.chen.fy.videophone.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.chen.fy.videophone.R;

public class VideoPlayer extends AppCompatActivity{

    private VideoView videoView;
    private Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);

        videoView = findViewById(R.id.video_player);
        //得到所传来的uri视频播放地址
        uri = getIntent().getData();
        videoView.setVideoURI(uri);

        //当底层编译器准备好之后回调
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();  //播放开始
            }
        });

        //当播放出错时回调
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(VideoPlayer.this, "播放出错!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //当播放完成时回调
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(VideoPlayer.this,"播放完成!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //设置控制面板
        videoView.setMediaController(new MediaController(this));
    }
}

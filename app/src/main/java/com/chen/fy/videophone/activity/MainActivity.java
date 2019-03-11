package com.chen.fy.videophone.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chen.fy.videophone.R;
import com.chen.fy.videophone.fragment.AudioFragment;
import com.chen.fy.videophone.fragment.NetAudioFragment;
import com.chen.fy.videophone.fragment.NetVideoFragment;
import com.chen.fy.videophone.fragment.VideoFragment;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private VideoFragment videoFragment;
    private NetVideoFragment netVideoFragment;
    private AudioFragment audioFragment;
    private NetAudioFragment netAudioFragment;

    private View search;
    private View game;
    private View history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup rg_main = findViewById(R.id.rg_main);
        search = findViewById(R.id.top_search);
        game = findViewById(R.id.top_game);
        history = findViewById(R.id.top_history);

        videoFragment = new VideoFragment();
        netVideoFragment = new NetVideoFragment();
        audioFragment = new AudioFragment();
        netAudioFragment = new NetAudioFragment();

        //第一次进入主界面显示本地视频界面
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_main,videoFragment).commitAllowingStateLoss();

        //设置点击事件
        rg_main.setOnCheckedChangeListener(this);
        search.setOnClickListener(this);
        game.setOnClickListener(this);
        history.setOnClickListener(this);
    }

    /**
     * 底部导航栏点击监听事件
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton = group.findViewById(checkedId);
        radioButton.setOnClickListener(this);
    }

    /**
     * 导航栏点击事件
     */
    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()){
            //顶部标题栏
            case R.id.top_search:
                Toast.makeText(MainActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.top_game:
                Toast.makeText(MainActivity.this, "游戏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.top_history:
                Toast.makeText(MainActivity.this, "历史", Toast.LENGTH_SHORT).show();
                break;

            //底部导航栏
            case R.id.rb_video:
                fragmentTransaction.replace(R.id.fragment_main,videoFragment);
                fragmentTransaction.commitAllowingStateLoss();
                break;
            case R.id.rb_audio:
                fragmentTransaction.replace(R.id.fragment_main,audioFragment);
                fragmentTransaction.commitAllowingStateLoss();
                break;
            case R.id.rb_net_video:
                fragmentTransaction.replace(R.id.fragment_main,netVideoFragment);
                fragmentTransaction.commitAllowingStateLoss();
                break;
            case R.id.rb_net_audio:
                fragmentTransaction.replace(R.id.fragment_main,netAudioFragment);
                fragmentTransaction.commitAllowingStateLoss();
                break;
        }
    }
}

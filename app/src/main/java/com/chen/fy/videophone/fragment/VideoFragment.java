package com.chen.fy.videophone.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.fy.videophone.R;
import com.chen.fy.videophone.adapter.VideoAdapter;
import com.chen.fy.videophone.beans.MediaInfo;

import java.util.ArrayList;

public class VideoFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tv_nothing_video;
    private ProgressBar pb_loading_video;
    private VideoAdapter videoAdapter;

    private ArrayList<MediaInfo> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_video);
        tv_nothing_video = view.findViewById(R.id.tv_nothing_video);
        pb_loading_video = view.findViewById(R.id.pb_loading_video);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        //获取权限,android6.0后不能简单的在AndroidManifest中声明权限而已
        if (getContext() != null) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //当没有被授权时
                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 1
                );
            } else {
                //被授权成功后
                initData();
            }
        }

    }

    //用来当在子线程中加载数据完成时跳转回主线程
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //主线程
            if (list != null && list.size() > 0) {
                tv_nothing_video.setVisibility(View.GONE);
                pb_loading_video.setVisibility(View.GONE);
                videoAdapter = new VideoAdapter(list, getContext());
                recyclerView.setAdapter(videoAdapter);
            } else {
                tv_nothing_video.setVisibility(View.VISIBLE);
                pb_loading_video.setVisibility(View.GONE);
            }
        }
    };


    private void initData() {
        new Thread() {    //在子线程中进行数据更新,查找本地视频可能耗时长
            @Override
            public void run() {
                if (getContext() != null) {
                    ContentResolver contentResolver = getContext().getContentResolver();  //获取内容提供器
                    Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;  //获取外部存储uri
                    String[] objects = {
                            MediaStore.Video.Media.DISPLAY_NAME,   //在sd卡中显示的名称
                            MediaStore.Video.Media.DURATION,       //视频的时长
                            MediaStore.Video.Media.SIZE,           //视频的大小
                            MediaStore.Video.Media.DATA            //视频的绝对地址
                    };
                    Cursor cursor = contentResolver.query(uri, objects, null, null, null);
                    if (cursor != null) {
                        list.clear();    //每次重新读取本地视频时,都先把数据集合清空
                        while (cursor.moveToNext()) {
                            MediaInfo mediaInfo = new MediaInfo();

                            String name = cursor.getString(0);
                            long duration = cursor.getLong(1);
                            long size = cursor.getLong(2);
                            String data = cursor.getString(3);

                            mediaInfo.setName(name);
                            mediaInfo.setDuration(duration);
                            mediaInfo.setSize(size);
                            mediaInfo.setData(data);

                            list.add(mediaInfo);
                        }
                        cursor.close();
                    }
                }
                handler.sendEmptyMessage(0);    //跳转到主线程
            }
        }.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //授权成功
                    initData();
                } else {
                    Toast.makeText(getContext(), "你没有这个权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:

        }
    }

}

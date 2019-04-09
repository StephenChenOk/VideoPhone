package com.chen.fy.videophone.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.chen.fy.videophone.IMediaService;
import com.chen.fy.videophone.beans.MediaInfo;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 音乐播放服务
 */
public class MediaService extends Service {

    private IMediaService.Stub stub = new IMediaService.Stub() {
        private MediaService mediaService = MediaService.this;

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void openAudio(int position) throws RemoteException {
            mediaService.openAudio(position);
        }

        @Override
        public void start() throws RemoteException {
            mediaService.start();
        }

        @Override
        public void pause() throws RemoteException {
            mediaService.pause();
        }

        @Override
        public void next() throws RemoteException {
            mediaService.next();
        }

        @Override
        public void pre() throws RemoteException {
            mediaService.pre();
        }

        @Override
        public int getPlayMode() throws RemoteException {
            return mediaService.getPlayMode();
        }

        @Override
        public void setPlayMode(int mode) throws RemoteException {
            mediaService.setPlayMode(mode);
        }

        @Override
        public int getCurrentPosition() throws RemoteException {
            return mediaService.getCurrentPosition();
        }

        @Override
        public int getDuration() throws RemoteException {
            return mediaService.getDuration();
        }

        @Override
        public String getName() throws RemoteException {
            return mediaService.getName();
        }

        @Override
        public String getSinger() throws RemoteException {
            return mediaService.getSinger();
        }

        @Override
        public void seekTO(int position) throws RemoteException {
            mediaService.seekTO(position);
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            return mediaService.isPlaying();
        }
    };

    /**
     * 要播放的音乐在列表中的位置
     */
    private int position;
    /**
     * 当前播放的歌曲
     */
    private MediaInfo mediaInfo;
    /**
     * 播放器
     */
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //获取音频列表数据
        initData();
    }

    /**
     * 根据位置播放音乐
     */
    private void openAudio(int position) {
        this.position = position;

        if (list != null && list.size() > 0) {
            mediaInfo = list.get(position);

            //当点击某一首歌曲进行播放时,应该先把上一次或者正在播放的歌曲停掉
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    start();   //开始播放
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    next();   //进行下一首
                    return true;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    next();
                }
            });
            try {
                mediaPlayer.setDataSource(mediaInfo.getData());
                mediaPlayer.prepareAsync();   //异步准备,本地资源和网络资源都可以
               // mediaPlayer.prepare();       //同步,本地资源可以
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "数据还没有准备好!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 开始播放音乐
     */
    private void start() {
        mediaPlayer.start();
    }

    /**
     * 暂停音乐
     */
    private void pause() {
        mediaPlayer.pause();
    }

    /**
     * 下一首
     */
    private void next() {

    }

    /**
     * 上一首
     */
    private void pre() {

    }

    /**
     * 得到播放模式
     */
    private int getPlayMode() {
        return 0;
    }

    /**
     * 设置播放模式
     */
    private void setPlayMode(int mode) {

    }

    /**
     * 得到当前播放进度
     */
    private int getCurrentPosition() {
        return -1;
    }

    /**
     * 得到当前总时长
     */
    private int getDuration() {
        return -1;
    }

    /**
     * 得到歌名
     */
    private String getName() {
        return "";
    }

    /**
     * 得到歌手
     */
    private String getSinger() {
        return "";
    }

    /**
     * 音乐播放位置的拖动
     */
    private void seekTO(int position) {

    }

    /**
     * 音乐是否正在播放
     */
    private boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    /**
     * 音频列表
     */
    private ArrayList<MediaInfo> list = new ArrayList<>();

    private void initData() {
        new Thread() {    //在子线程中进行数据更新,查找本地视频可能耗时长
            @Override
            public void run() {
                ContentResolver contentResolver = getContentResolver();  //获取内容提供器
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;  //获取外部存储uri
                String[] objects = {
                        MediaStore.Audio.Media.DISPLAY_NAME,   //在sd卡中显示的名称
                        MediaStore.Audio.Media.DURATION,       //音频的时长
                        MediaStore.Audio.Media.SIZE,           //音频的大小
                        MediaStore.Audio.Media.DATA            //音频的绝对地址
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
        }.start();
    }
}

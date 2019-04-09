// IMediaService.aidl
package com.chen.fy.videophone;

// Declare any non-default types here with import statements

interface IMediaService {

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                            double aDouble, String aString);

    /**
     * 根据位置播放音乐
     */
    void openAudio(int position);

    /**
     * 开始播放音乐
     */
    void start();

    /**
     * 暂停音乐
     */
    void pause();

    /**
     * 下一首
     */
    void next();

    /**
     * 上一首
     */
    void pre();

    /**
     * 得到播放模式
     */
    int getPlayMode();

    /**
     * 设置播放模式
     */
    void setPlayMode(int mode);

    /**
     * 得到当前播放进度
     */
    int getCurrentPosition();

    /**
     * 得到当前总时长
     */
    int getDuration();

    /**
     * 得到歌名
     */
    String getName();

    /**
     * 得到歌手
     */
    String getSinger();

    /**
     * 音乐播放位置的拖动
     */
    void seekTO(int position);

    /**
     * 音乐是否正在播放
     */
    boolean isPlaying();

}

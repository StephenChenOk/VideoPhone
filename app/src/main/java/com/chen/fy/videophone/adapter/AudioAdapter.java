package com.chen.fy.videophone.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.fy.videophone.R;
import com.chen.fy.videophone.activity.AudioPlayer;
import com.chen.fy.videophone.activity.VideoPlayer;
import com.chen.fy.videophone.beans.MediaInfo;
import com.chen.fy.videophone.utils.Util;

import java.util.ArrayList;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {

    private ArrayList<MediaInfo> list;
    private Context context;

    public AudioAdapter(ArrayList<MediaInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.audio_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        MediaInfo mediaInfo = list.get(i);
        viewHolder.tv_name.setText(mediaInfo.getName());
        viewHolder.tv_duration.setText(Util.stringForTime((int) mediaInfo.getDuration()));
        //文件大小转换(按照未见大小从byte转化为各个文件大小单位,)
        viewHolder.tv_size.setText(Formatter.formatFileSize(context, mediaInfo.getSize()));

        //item设置点击事件

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AudioPlayer.class);
                //播放列表中的某个音频
                intent.putExtra("position",i);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name;
        private TextView tv_duration;
        private TextView tv_size;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.video_name);
            tv_duration = itemView.findViewById(R.id.video_duration);
            tv_size = itemView.findViewById(R.id.video_size);
        }
    }
}

package com.yk.luckpan;

import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.yk.view.VideoListItem;
import com.volokh.danylo.video_player_manager.ui.MediaPlayerWrapper;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

import java.util.List;
/**
 * Created by yukun on 17-10-12.
 */

public class VideoWatchAdapter extends RecyclerView.Adapter<VideoWatchAdapter.VideoViewHolder>{
    private final List<VideoListItem> mList; // 视频项列表

    // 构造器
    public VideoWatchAdapter(List<VideoListItem> list) {
        mList = list;
    }

    @Override
    public VideoWatchAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        // 必须要设置Tag, 否则无法显示
        VideoWatchAdapter.VideoViewHolder holder = new VideoWatchAdapter.VideoViewHolder(view);
        view.setTag(holder);
        return new VideoWatchAdapter.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoWatchAdapter.VideoViewHolder holder, int position) {
        VideoListItem videoItem = mList.get(position);
        holder.bindTo(videoItem);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoPlayerView mVpvPlayer; // 播放控件
        ImageView mIvCover; // 覆盖层
        TextView mTvTitle; // 标题

        private Context mContext;
        private MediaPlayerWrapper.MainThreadMediaPlayerListener mPlayerListener;

        public VideoViewHolder(View itemView) {
            super(itemView);
            mVpvPlayer = (VideoPlayerView) itemView.findViewById(R.id.item_video_vpv_player); // 播放控件
            mTvTitle = (TextView) itemView.findViewById(R.id.item_video_tv_title);
            mIvCover = (ImageView) itemView.findViewById(R.id.item_video_iv_cover);


            mContext = itemView.getContext().getApplicationContext();
            mPlayerListener = new MediaPlayerWrapper.MainThreadMediaPlayerListener() {
                @Override
                public void onVideoSizeChangedMainThread(int width, int height) {
                }

                @Override
                public void onVideoPreparedMainThread() {
                    // 视频播放隐藏前图
                    mIvCover.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onVideoCompletionMainThread() {
                }

                @Override
                public void onErrorMainThread(int what, int extra) {

                }

                @Override
                public void onBufferingUpdateMainThread(int percent) {
                }

                @Override
                public void onVideoStoppedMainThread() {
                    // 视频暂停显示前图
                    mIvCover.setVisibility(View.VISIBLE);
                }
            };

            mVpvPlayer.addMediaPlayerListener(mPlayerListener);
        }

        public void bindTo(VideoListItem vli) {
            mTvTitle.setText(vli.getTitle());
//            mIvCover.setImageURI(Uri.parse(vli.getCoverImageUrl()));
            Glide.with(mContext).load(vli.getCoverImageUrl()).into(mIvCover);

        }

        // 返回播放器
        public VideoPlayerView getVpvPlayer() {
            return mVpvPlayer;
        }

    }
}

package com.code12.anycast.View.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.code12.anycast.AcApplication;
import com.code12.anycast.Model.types.NormalVListInfo;
import com.code12.anycast.Model.types.NormalVideoInfo;
import com.code12.anycast.R;
import com.code12.anycast.View.Views.CircleImageView;
import com.code12.anycast.ViewModel.NormalViewModel;
import com.code12.anycast.auxilliary.utils.LogUtil;
import com.code12.anyplayer.VideoPlayActivity;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NormalVAdapter extends RecyclerView.Adapter {
    private Context context;
    private NormalVListInfo mVideoInfo;
    private NormalViewModel mVideoModel;

    private static final int TYPE_ENTRANCE = 0;
    private static final int TYPE_GAME_ITEM = 1;

    public NormalVAdapter(Context context) {
        this.context = context;
        mVideoModel = new NormalViewModel(AcApplication.getInstance());
    }

    public void setVideoInfo(NormalVListInfo vinfo) {
        this.mVideoInfo = vinfo;
    }

    @SuppressLint("InflateParams")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_ENTRANCE:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_game_entrance, null);
                return new GameEntranceViewHolder(view);
            case TYPE_GAME_ITEM:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_game_partition, null);
                return new GameItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final NormalVListInfo.MediaBean resultBean;
        if (holder instanceof GameEntranceViewHolder) {
            GameEntranceViewHolder gameEntranceViewHolder = (GameEntranceViewHolder) holder;
            gameEntranceViewHolder.title.setText(mVideoInfo.getResult().get(position).getVideo().getTitle());
            Glide.with(context)
                    .load(mVideoInfo.getResult().get(position).getVideo().getCoverurl())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.bili_default_image_tv)
                    .dontAnimate()
                    .into(((GameEntranceViewHolder) holder).image);
        } else if (holder instanceof GameItemViewHolder) {
            NormalVAdapter.GameItemViewHolder itemViewHolder = (NormalVAdapter.GameItemViewHolder) holder;
            resultBean = mVideoInfo.getResult().get(position);

            Glide.with(context)
                    .load(resultBean.getVideo().getCoverurl())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.bili_default_image_tv)
                    .dontAnimate()
                    .into(itemViewHolder.itemGameCover);

            Glide.with(context)
                    .load(resultBean.getVideo().getCoverurl())
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.mipmap.ico_user_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemViewHolder.itemGameUserCover);

            itemViewHolder.itemGameTitle.setText(resultBean.getVideo().getTitle());
            itemViewHolder.itemGameUser.setText(resultBean.getVideo().getTitle());
            itemViewHolder.itemGameCount.setText(resultBean.getVideo().getVid());
            itemViewHolder.itemGameLayout.setTag(position);
            itemViewHolder.itemGameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startPlay(mVideoInfo.getResult().get((int)v.getTag()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mVideoInfo != null) {
            return mVideoInfo.getResult().size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_GAME_ITEM;
    }

    static class GameEntranceViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;

        GameEntranceViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.game_entrance_title);
            image = itemView.findViewById(R.id.game_entrance_image);
        }
    }

    static class GameItemViewHolder extends RecyclerView.ViewHolder
    {
//        @BindView(R.id.item_game_cover)
        ImageView itemGameCover;
//        @BindView(R.id.item_game_user)
        TextView itemGameUser;
//        @BindView(R.id.item_game_title)
        TextView itemGameTitle;
//        @BindView(R.id.item_game_user_cover)
        CircleImageView itemGameUserCover;
//        @BindView(R.id.item_game_count)
        TextView itemGameCount;
//        @BindView(R.id.item_game_layout)
        CardView itemGameLayout;

        GameItemViewHolder(View itemView) {
            super(itemView);
            itemGameCover = itemView.findViewById(R.id.item_game_cover);
            itemGameUser = itemView.findViewById(R.id.item_game_user);
            itemGameTitle = itemView.findViewById(R.id.item_game_title);
            itemGameUserCover = itemView.findViewById(R.id.item_game_user_cover);
            itemGameCount = itemView.findViewById(R.id.item_game_count);
            itemGameLayout = itemView.findViewById(R.id.item_game_layout);
        }
    }

    private void startPlay(NormalVListInfo.MediaBean resultBean){
        mVideoModel.getPlayUrlObservable(resultBean.getVideo().getVid()).subscribe(
                body -> {
                    startPlay((NormalVideoInfo) body);
                },
                throwable -> {
                    LogUtil.test(throwable.toString());
                });
    }

    private void startPlay(NormalVideoInfo v) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra(VideoPlayActivity.EXTRA_TITLE, v.getResult().get(0).getJobId());
        intent.putExtra(VideoPlayActivity.EXTRA_URL, v.getResult().get(0).getVideoUrl());
        this.context.startActivity(intent);
    }
}

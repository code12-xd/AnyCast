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
import com.code12.anycast.Model.types.SampleInfo;
import com.code12.anycast.R;
import com.code12.anycast.View.Activitys.VideoPlayActivity;
import com.code12.anycast.View.Views.CircleImageView;
import com.code12.anycast.ViewModel.SampleViewModel;
import com.code12.anycast.auxilliary.utils.LogUtil;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SampleAdapter extends RecyclerView.Adapter {
    private Context context;
    private SampleInfo mInfo;

    private static final int TYPE_ENTRANCE = 0;
    private static final int TYPE_GAME_ITEM = 1;

    public SampleAdapter(Context context) {
        this.context = context;
    }

    public void setSampleInfo(SampleInfo gameinfo) {
        this.mInfo = gameinfo;
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
        final SampleInfo.ResultBean resultBean;
        if (holder instanceof GameEntranceViewHolder) {
            GameEntranceViewHolder gameEntranceViewHolder = (GameEntranceViewHolder) holder;
            gameEntranceViewHolder.title.setText(mInfo.getResult().get(position).getName());
        } else if (holder instanceof GameItemViewHolder) {
            SampleAdapter.GameItemViewHolder itemViewHolder = (SampleAdapter.GameItemViewHolder) holder;
            resultBean = mInfo.getResult().get(position);

            itemViewHolder.itemGameTitle.setText(resultBean.getName());
            itemViewHolder.itemGameLayout.setTag(position);
            itemViewHolder.itemGameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startPlay(mInfo.getResult().get((int)v.getTag()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mInfo != null) {
            return mInfo.getResult().size();
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

    private void startPlay(SampleInfo.ResultBean v) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra(VideoPlayActivity.EXTRA_TITLE, v.getName());
        intent.putExtra(VideoPlayActivity.EXTRA_URL, v.getUrl());
        this.context.startActivity(intent);
    }
}

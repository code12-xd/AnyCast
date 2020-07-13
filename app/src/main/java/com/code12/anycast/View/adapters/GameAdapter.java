package com.code12.anycast.View.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.code12.anycast.AcApplication;
import com.code12.anycast.Model.types.GameInfo;
import com.code12.anycast.R;
import com.code12.anycast.View.Views.CircleImageView;
import com.code12.anycast.ViewModel.VideoBeanViewModel;
import com.code12.anycast.auxilliary.utils.LogUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;

public class GameAdapter extends RecyclerView.Adapter {
    private Context context;
    private GameInfo mGameInfo;
    private VideoBeanViewModel mVideoModel;

    private static final int TYPE_ENTRANCE = 0;
    private static final int TYPE_GAME_ITEM = 1;

    public GameAdapter(Context context) {
        this.context = context;
        mVideoModel = new VideoBeanViewModel(AcApplication.getInstance());
    }

    public void setGameInfo(GameInfo gameinfo) {
        this.mGameInfo = gameinfo;
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
        final GameInfo.ResultBean resultBean;
        if (holder instanceof GameEntranceViewHolder) {
            GameEntranceViewHolder gameEntranceViewHolder = (GameEntranceViewHolder) holder;
            gameEntranceViewHolder.title.setText(mGameInfo.getResult().get(position).getName());
            Glide.with(context)
                    .load(mGameInfo.getResult().get(position).getSrc())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.bili_default_image_tv)
                    .dontAnimate()
                    .into(((GameEntranceViewHolder) holder).image);
        } else if (holder instanceof GameItemViewHolder) {
            GameAdapter.GameItemViewHolder itemViewHolder = (GameAdapter.GameItemViewHolder) holder;
            resultBean = mGameInfo.getResult().get(position);

            Glide.with(context)
                    .load(resultBean.getSrc())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.bili_default_image_tv)
                    .dontAnimate()
                    .into(itemViewHolder.itemGameCover);

            Glide.with(context)
                    .load(resultBean.getSrc())
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.mipmap.ico_user_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemViewHolder.itemGameUserCover);

            itemViewHolder.itemGameTitle.setText(resultBean.getName());
            itemViewHolder.itemGameUser.setText(resultBean.getNickName());
            itemViewHolder.itemGameCount.setText(String.valueOf(resultBean.getOnline()));
            itemViewHolder.itemGameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startPlay(resultBean);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mGameInfo != null) {
            return mGameInfo.getResult().size();
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

    public void startPlay(GameInfo.ResultBean resultBean){
        mVideoModel.parseUrl(resultBean.getUrl());
    }

    private String parseLiveUrl(ResponseBody responseBody) {
        String xml = null;
        try {
            xml = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.d("xml", xml);
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        assert document != null;
        Element rootElement = document.getRootElement();
        Element durlElement = rootElement.element("durl");
        Element urlElement = durlElement.element("url");
        String url = urlElement.getText();
        return url;
    }
}

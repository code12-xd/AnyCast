package com.code12.anycast.View.section;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.code12.anycast.R;
import com.code12.anycast.Model.types.RecommendInfo;
import com.code12.anycast.View.section.StatelessSection;
import com.code12.anycast.auxilliary.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class HomeRecommendedSection extends StatelessSection {

    private Context mContext;
    private String title;
    private String type;
    private int liveCount;
    private static final String TYPE_RECOMMENDED = "recommend";
    private static final String TYPE_LIVE = "live";
    private static final String TYPE_BANGUMI = "bangumi_2";
    private static final String GOTO_BANGUMI = "bangumi_list";
    private static final String TYPE_ACTIVITY = "activity";
    private List<RecommendInfo.ResultBean.BodyBean> datas = new ArrayList<>();
    private final Random mRandom;

    private int[] icons = new int[]{
            R.mipmap.ic_header_hot, R.mipmap.ic_head_live,
            R.mipmap.ic_category_t13, R.mipmap.ic_category_t1,
            R.mipmap.ic_category_t3, R.mipmap.ic_category_t129,
            R.mipmap.ic_category_t4, R.mipmap.ic_category_t119,
            R.mipmap.ic_category_t36, R.mipmap.ic_category_t160,
            R.mipmap.ic_category_t155, R.mipmap.ic_category_t5,
            R.mipmap.ic_category_t11, R.mipmap.ic_category_t23
    };

    public HomeRecommendedSection(Context context, String title, String type, int liveCount,
                                  List<RecommendInfo.ResultBean.BodyBean> datas) {
        super(R.layout.layout_home_recommend_head, R.layout.layout_home_recommend_foot, R.layout.layout_home_recommend_boby);

        this.mContext = context;
        this.title = title;
        this.type = type;
        this.liveCount = liveCount;
        this.datas = datas;

        mRandom = new Random();
    }

    @Override
    public int getContentItemsTotal() {
        return datas.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final RecommendInfo.ResultBean.BodyBean bodyBean = datas.get(position);

        Glide.with(mContext)
                .load(Uri.parse(bodyBean.getCover()))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.bili_default_image_tv)
                .dontAnimate()
                .into(itemViewHolder.mVideoImg);

        itemViewHolder.mVideoTitle.setText(bodyBean.getTitle());
        itemViewHolder.mCardView.setOnClickListener(v -> {

            String gotoX = bodyBean.getGotoX();
            switch (gotoX)
            {
                case TYPE_LIVE:
//                    LivePlayerActivity.launch((Activity) mContext,
//                            Integer.valueOf(bodyBean.getParam()), bodyBean.getTitle(),
//                            bodyBean.getOnline(), bodyBean.getUpFace(), bodyBean.getUp(), 0);
                    break;
                case GOTO_BANGUMI:

                    break;
                default:
                    //TODO: ??
//                    VideoDetailsActivity.launch((Activity) mContext,
//                            bodyBean.getParam(), bodyBean.getCover());
                    break;
            }
        });

        switch (type)
        {
            case TYPE_LIVE:
                //直播item
                itemViewHolder.mLiveLayout.setVisibility(View.VISIBLE);
                itemViewHolder.mVideoLayout.setVisibility(View.GONE);
                itemViewHolder.mBangumiLayout.setVisibility(View.GONE);
                itemViewHolder.mLiveUp.setText(bodyBean.getUp());
                itemViewHolder.mLiveOnline.setText(String.valueOf(bodyBean.getOnline()));
                break;
            case TYPE_BANGUMI:
                // 番剧item
                itemViewHolder.mLiveLayout.setVisibility(View.GONE);
                itemViewHolder.mVideoLayout.setVisibility(View.GONE);
                itemViewHolder.mBangumiLayout.setVisibility(View.VISIBLE);
                itemViewHolder.mBangumiUpdate.setText(bodyBean.getDesc1());
                break;
            case TYPE_ACTIVITY:
                ViewGroup.LayoutParams layoutParams = itemViewHolder.mCardView.getLayoutParams();
                layoutParams.height = DisplayUtil.dp2px(mContext, 200f);
                itemViewHolder.mCardView.setLayoutParams(layoutParams);
                itemViewHolder.mLiveLayout.setVisibility(View.GONE);
                itemViewHolder.mVideoLayout.setVisibility(View.GONE);
                itemViewHolder.mBangumiLayout.setVisibility(View.GONE);
                break;
            default:
                itemViewHolder.mLiveLayout.setVisibility(View.GONE);
                itemViewHolder.mBangumiLayout.setVisibility(View.GONE);
                itemViewHolder.mVideoLayout.setVisibility(View.VISIBLE);
                itemViewHolder.mVideoPlayNum.setText(bodyBean.getPlay());
                itemViewHolder.mVideoReviewCount.setText(bodyBean.getDanmaku());
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        setTypeIcon(headerViewHolder);
        headerViewHolder.mTypeTv.setText(title);
//        headerViewHolder.mTypeRankBtn.setOnClickListener(v -> mContext.startActivity(
//                new Intent(mContext, OriginalRankActivity.class)));

        switch (type) {
            case TYPE_RECOMMENDED:
                headerViewHolder.mTypeMore.setVisibility(View.GONE);
                headerViewHolder.mTypeRankBtn.setVisibility(View.VISIBLE);
                headerViewHolder.mAllLiveNum.setVisibility(View.GONE);
                break;
            case TYPE_LIVE:
                headerViewHolder.mTypeRankBtn.setVisibility(View.GONE);
                headerViewHolder.mTypeMore.setVisibility(View.VISIBLE);
                headerViewHolder.mAllLiveNum.setVisibility(View.VISIBLE);
                SpannableStringBuilder stringBuilder = new SpannableStringBuilder("当前" + liveCount + "个直播");
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(
                        mContext.getResources().getColor(R.color.pink));
                stringBuilder.setSpan(foregroundColorSpan, 2,
                        stringBuilder.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                headerViewHolder.mAllLiveNum.setText(stringBuilder);
                break;
            default:
                headerViewHolder.mTypeRankBtn.setVisibility(View.GONE);
                headerViewHolder.mTypeMore.setVisibility(View.VISIBLE);
                headerViewHolder.mAllLiveNum.setVisibility(View.GONE);
                break;
        }
    }

    private void setTypeIcon(HeaderViewHolder headerViewHolder) {
        switch (title) {
            case "热门焦点":
                headerViewHolder.mTypeImg.setImageResource(icons[0]);
                break;
            case "正在直播":
                headerViewHolder.mTypeImg.setImageResource(icons[1]);
                break;
            case "番剧推荐":
                headerViewHolder.mTypeImg.setImageResource(icons[2]);
                break;
            case "动画区":
                headerViewHolder.mTypeImg.setImageResource(icons[3]);
                break;
            case "音乐区":
                headerViewHolder.mTypeImg.setImageResource(icons[4]);
                break;
            case "舞蹈区":
                headerViewHolder.mTypeImg.setImageResource(icons[5]);
                break;
            case "游戏区":
                headerViewHolder.mTypeImg.setImageResource(icons[6]);
                break;
            case "鬼畜区":
                headerViewHolder.mTypeImg.setImageResource(icons[7]);
                break;
            case "科技区":
                headerViewHolder.mTypeImg.setImageResource(icons[8]);
                break;
            case "生活区":
                headerViewHolder.mTypeImg.setImageResource(icons[9]);
                break;
            case "时尚区":
                headerViewHolder.mTypeImg.setImageResource(icons[10]);
                break;
            case "娱乐区":
                headerViewHolder.mTypeImg.setImageResource(icons[11]);
                break;
            case "电视剧区":
                headerViewHolder.mTypeImg.setImageResource(icons[12]);
                break;
            case "电影区":
                headerViewHolder.mTypeImg.setImageResource(icons[13]);
                break;
        }
    }


    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FootViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        final FootViewHolder footViewHolder = (FootViewHolder) holder;
        footViewHolder.mDynamic.setText(String.valueOf(mRandom.nextInt(200)) + "条新动态,点这里刷新");
        footViewHolder.mRefreshBtn.setOnClickListener(v -> footViewHolder.mRefreshBtn
                .animate()
                .rotation(360)
                .setInterpolator(new LinearInterpolator())
                .setDuration(1000).start());

        footViewHolder.mRecommendRefresh.setOnClickListener(v -> footViewHolder.mRecommendRefresh
                .animate()
                .rotation(360)
                .setInterpolator(new LinearInterpolator())
                .setDuration(1000).start());
//        footViewHolder.mBangumiIndexBtn.setOnClickListener(v -> mContext.startActivity(
//                new Intent(mContext, BangumiIndexActivity.class)));

//        footViewHolder.mBangumiTimelineBtn.setOnClickListener(v -> mContext.startActivity(
//                new Intent(mContext, BangumiScheduleActivity.class)));

        switch (type) {
            case TYPE_RECOMMENDED:
                footViewHolder.mMoreBtn.setVisibility(View.GONE);
                footViewHolder.mRefreshLayout.setVisibility(View.GONE);
                footViewHolder.mBangumiLayout.setVisibility(View.GONE);
                footViewHolder.mRecommendRefreshLayout.setVisibility(View.VISIBLE);
                break;
            case TYPE_BANGUMI:
                footViewHolder.mMoreBtn.setVisibility(View.GONE);
                footViewHolder.mRefreshLayout.setVisibility(View.GONE);
                footViewHolder.mRecommendRefreshLayout.setVisibility(View.GONE);
                footViewHolder.mBangumiLayout.setVisibility(View.VISIBLE);
                break;
            case TYPE_ACTIVITY:
                footViewHolder.mRecommendRefreshLayout.setVisibility(View.GONE);
                footViewHolder.mBangumiLayout.setVisibility(View.GONE);
                footViewHolder.mMoreBtn.setVisibility(View.GONE);
                footViewHolder.mRefreshLayout.setVisibility(View.GONE);
                break;
            default:
                footViewHolder.mRecommendRefreshLayout.setVisibility(View.GONE);
                footViewHolder.mBangumiLayout.setVisibility(View.GONE);
                footViewHolder.mMoreBtn.setVisibility(View.VISIBLE);
                footViewHolder.mRefreshLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.item_type_img)
        ImageView mTypeImg;
//        @BindView(R.id.item_type_tv)
        TextView mTypeTv;
//        @BindView(R.id.item_type_more)
        TextView mTypeMore;
//        @BindView(R.id.item_type_rank_btn)
        TextView mTypeRankBtn;
//        @BindView(R.id.item_live_all_num)
        TextView mAllLiveNum;

        HeaderViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
            mTypeImg = itemView.findViewById(R.id.item_type_img);
            mTypeTv = itemView.findViewById(R.id.item_type_tv);
            mTypeMore = itemView.findViewById(R.id.item_type_more);
            mTypeRankBtn = itemView.findViewById(R.id.item_type_rank_btn);
            mAllLiveNum = itemView.findViewById(R.id.item_live_all_num);
        }
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.card_view)
        CardView mCardView;

//        @BindView(R.id.video_preview)
        ImageView mVideoImg;

//        @BindView(R.id.video_title)
        TextView mVideoTitle;

//        @BindView(R.id.video_play_num)
        TextView mVideoPlayNum;

//        @BindView(R.id.video_review_count)
        TextView mVideoReviewCount;

//        @BindView(R.id.layout_live)
        RelativeLayout mLiveLayout;

//        @BindView(R.id.layout_video)
        LinearLayout mVideoLayout;

//        @BindView(R.id.item_live_up)
        TextView mLiveUp;

//        @BindView(R.id.item_live_online)
        TextView mLiveOnline;

//        @BindView(R.id.layout_bangumi)
        RelativeLayout mBangumiLayout;

//        @BindView(R.id.item_bangumi_update)
        TextView mBangumiUpdate;


        public ItemViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
            mCardView = itemView.findViewById(R.id.card_view);
            mVideoImg = itemView.findViewById(R.id.video_preview);
            mVideoTitle = itemView.findViewById(R.id.video_title);
            mVideoPlayNum = itemView.findViewById(R.id.video_play_num);
            mVideoReviewCount = itemView.findViewById(R.id.video_review_count);
            mLiveLayout = itemView.findViewById(R.id.layout_live);
            mVideoLayout = itemView.findViewById(R.id.layout_video);
            mLiveUp = itemView.findViewById(R.id.item_live_up);
            mLiveOnline = itemView.findViewById(R.id.item_live_online);
            mBangumiLayout = itemView.findViewById(R.id.layout_bangumi);
            mBangumiUpdate = itemView.findViewById(R.id.item_bangumi_update);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.item_btn_more)
        Button mMoreBtn;

//        @BindView(R.id.item_dynamic)
        TextView mDynamic;

//        @BindView(R.id.item_btn_refresh)
        ImageView mRefreshBtn;

//        @BindView(R.id.item_refresh_layout)
        LinearLayout mRefreshLayout;

//        @BindView(R.id.item_recommend_refresh_layout)
        LinearLayout mRecommendRefreshLayout;

//        @BindView(R.id.item_recommend_refresh)
        ImageView mRecommendRefresh;

//        @BindView(R.id.item_bangumi_layout)
        LinearLayout mBangumiLayout;

//        @BindView(R.id.item_btn_bangumi_index)
        ImageView mBangumiIndexBtn;

//        @BindView(R.id.item_btn_bangumi_timeline)
        ImageView mBangumiTimelineBtn;

        FootViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
            mMoreBtn = itemView.findViewById(R.id.item_btn_more);
            mDynamic = itemView.findViewById(R.id.item_dynamic);
            mRefreshBtn = itemView.findViewById(R.id.item_btn_refresh);
            mRefreshLayout = itemView.findViewById(R.id.item_refresh_layout);
            mRecommendRefreshLayout = itemView.findViewById(R.id.item_recommend_refresh_layout);
            mRecommendRefresh = itemView.findViewById(R.id.item_recommend_refresh);
            mBangumiLayout = itemView.findViewById(R.id.item_bangumi_layout);
            mBangumiIndexBtn = itemView.findViewById(R.id.item_btn_bangumi_index);
            mBangumiTimelineBtn = itemView.findViewById(R.id.item_btn_bangumi_timeline);
        }
    }
}

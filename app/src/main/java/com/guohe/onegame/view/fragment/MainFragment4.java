package com.guohe.onegame.view.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.RefreshUtil;
import com.guohe.onegame.view.adapter.MineDynamicGridAdapter;
import com.guohe.onegame.view.mine.SettingActivity;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by 水寒 on 2017/8/7.
 */

public class MainFragment4 extends BaseMainFragment implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private MineDynamicGridAdapter mAdapter;
    private SimpleDraweeView mHeaderDraw;
    private PtrFrameLayout mPtrFrameLayout;
    private int mUserId;
    private boolean mIsMine;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_main_page4;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        if(bundle != null){
            mUserId = bundle.getInt("userid");
            mIsMine = mUserId == 1;
        }else{
            mIsMine = true;
            mUserId = 1;
        }
        mHeaderDraw = getView(R.id.header_icon);
        ImageButton attentionButton = getView(R.id.attention_icon);
        attentionButton.setOnClickListener(this);
        if(mIsMine){
            attentionButton.setImageResource(R.mipmap.icon_setting);
        }else{
            attentionButton.setImageResource(R.mipmap.icon_not_followed);
        }
        getView(R.id.header_icon).setOnClickListener(this);
        getView(R.id.mine_role).setOnClickListener(this);
        getView(R.id.mine_credit_score).setOnClickListener(this);
        getView(R.id.mine_menu_dynamic).setOnClickListener(this);
        getView(R.id.mine_menu_myfollowed).setOnClickListener(this);
        getView(R.id.mine_menu_followme).setOnClickListener(this);
        getView(R.id.mine_wallet).setOnClickListener(this);
        getView(R.id.mine_progress).setOnClickListener(this);
        mRecyclerView = getView(R.id.personal_recyclerview);
        bindRecyclerView();
        mPtrFrameLayout = refreshView(R.id.main_mine_refreshview, new RefreshUtil.OnRefresh() {
            @Override
            public void refreshBegin(PtrFrameLayout frame) {

            }
        });
        AppBarLayout appBarLayout = getView(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    mPtrFrameLayout.setEnabled(true);
                } else {
                    mPtrFrameLayout.setEnabled(false);
                }
            }
        });
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration(){
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildLayoutPosition(view);
                if(position % 3 == 0){
                    outRect.top = 1;
                }else{
                    outRect.top = 1;
                    outRect.left = 1;
                }
            }
        });
        mAdapter = new MineDynamicGridAdapter(this.getContext());
        //HeaderAndFooterRecyclerViewAdapter headAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mAdapter);
       /* View headerView = LayoutInflater.from(
                this.getContext()).inflate(R.layout.header_mine_dynamic_list, null);
        RecyclerViewUtils.setHeaderView(mRecyclerView, headerView);*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.attention_icon:
                if(mIsMine){
                    SettingActivity.startActivity(MainFragment4.this.getContext());
                }else{
                    //TODO 判断是否关注了

                }
                break;
            case R.id.header_icon:
                if(mIsMine){  //设置头像

                }
                break;
            case R.id.mine_role:

                break;
            case R.id.mine_credit_score:

                break;
            case R.id.mine_menu_dynamic:

                break;
            case R.id.mine_menu_myfollowed:

                break;
            case R.id.mine_menu_followme:

                break;
            case R.id.mine_wallet:

                break;
            case R.id.mine_progress:

                break;
        }
    }
}

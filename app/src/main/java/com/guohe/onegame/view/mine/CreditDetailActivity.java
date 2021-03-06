package com.guohe.onegame.view.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.view.base.BaseActivity;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/25.
 * 信用积分详情
 */

public class CreditDetailActivity extends BaseActivity{

    private RecyclerView mRecyclerView;
    private CreditDetailAdapter mAdapter;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("积分详情");
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_credit_detail;
    }

    @Override
    protected void initView() {
        mRecyclerView = getView(R.id.credit_recyclerview);
        bindRecyclerView();
    }

    private void bindRecyclerView(){
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CreditDetailAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, CreditDetailActivity.class);
        context.startActivity(intent);
    }

    class CreditDetailAdapter extends RecyclerView.Adapter<CreditDetailViewHolder>{

        @Override
        public CreditDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CreditDetailViewHolder(LayoutInflater.from(CreditDetailActivity.this)
                    .inflate(R.layout.item_credit_detail, parent, false));
        }

        @Override
        public void onBindViewHolder(CreditDetailViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    class CreditDetailViewHolder extends RecyclerView.ViewHolder{

        public CreditDetailViewHolder(View itemView) {
            super(itemView);
        }
    }
}

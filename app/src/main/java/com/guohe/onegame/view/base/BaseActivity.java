package com.guohe.onegame.view.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.MvpView;
import com.guohe.onegame.manage.rxbus.RxBus;
import com.guohe.onegame.manage.rxbus.bean.BaseBusEvent;
import com.guohe.onegame.util.LogUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by 水寒 on 2017/7/14.
 */

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private BaseView mBaseView;
    private List<Subscription> mSubscriptions = new ArrayList<>();

    public BaseActivity(){
        mBaseView = new BaseView();
    }

    private List<MvpPresenter> mPresenters = new ArrayList<>();
    private SwipeRefreshLayout mRefreshView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        setStatuBar();
        initView();
        initPresenter(mPresenters);
        for(MvpPresenter presenter : mPresenters){
            if(presenter == null) continue;
            presenter.attachView(this);
        }
        initData();
        mBaseView.onCreate(this);
    }

    protected void setStatuBar(){
        StatusBarUtil.setTranslucent(this, 0);
    }

    protected boolean youMenResume(){
        return false;
    }

    protected boolean youMenPause(){
        return false;
    }

    /**
     * 设置布局文件
     * @return
     */
    protected abstract int getContentView();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onPause() {
        super.onPause();
        mBaseView.onPause(this);
        if(!youMenPause()){
            //MobclickAgent.onPause(this);
           // MobclickAgent.onPageEnd(this.getClass().getName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBaseView.onResume(this);
        if(!youMenResume()) {
            //MobclickAgent.onResume(this);
            //MobclickAgent.onPageStart(this.getClass().getName());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaseView.onDestroy(this);
        for(MvpPresenter presenter : mPresenters){
            if(presenter == null) continue;
            presenter.dettachView();
        }
        mPresenters.clear();
        for(Subscription subscription : mSubscriptions){
            if(subscription == null) continue;
            if(subscription.isUnsubscribed()) continue;
            subscription.unsubscribe();
        }
        mSubscriptions.clear();
    }

    @SuppressWarnings("unchecked")
    public final <E extends View> E getView (int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            LogUtil.e("Could not cast View to concrete class.", ex);
            throw ex;
        }
    }

    protected <E extends BaseBusEvent> void observerRxBus(Class<E> busClass, final Action1<E> onNext) {
        Subscription subscription = RxBus.getDefault().observerRxBus(busClass, onNext);
        mSubscriptions.add(subscription);
    }

    protected <E extends BaseBusEvent> void observerRxBusSticky(Class<E> busClass, final Action1<E> onNext) {
        Subscription subscription = RxBus.getDefault().observerRxBusSticky(busClass, onNext);
        mSubscriptions.add(subscription);
    }

    /**
     * 刷新界面
     * @param id
     * @param onRefresh
     */
    public SwipeRefreshLayout refreshView(int id, final SwipeRefreshLayout.OnRefreshListener onRefresh) {
        SwipeRefreshLayout refreshView = getView(id);
        if (refreshView == null) return null;
        mRefreshView = refreshView;
        if(mRefreshView.isRefreshing()) return mRefreshView;
        mBaseView.refreshView(refreshView, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefresh.onRefresh();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshView.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        return refreshView;
    }
}
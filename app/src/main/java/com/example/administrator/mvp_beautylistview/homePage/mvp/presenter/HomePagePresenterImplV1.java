package com.example.administrator.mvp_beautylistview.homePage.mvp.presenter;

import android.content.Context;

import com.example.administrator.mvp_beautylistview.homePage.mvp.constract.HomePageContract;
import com.example.administrator.mvp_beautylistview.homePage.mvp.model.HomePageModelImplV1;
import com.example.administrator.mvp_beautylistview.homePage.mvp.model.HomePageModelImplV2;
import com.example.administrator.mvp_beautylistview.homePage.mvp.model.entity.bean.ImageInfo;
import java.util.ArrayList;

/**
 * Presenter:
 *  用于从网络中获取数据
 *      如果获取不到, 从数据库当中取缓存数据
 */

public class HomePagePresenterImplV1 implements HomePageContract.Presenter {
    private HomePageContract.View mView;
    //数据库model
    private HomePageContract.Model mModelV2;
    public HomePagePresenterImplV1(HomePageContract.View view) {
        mView = view;
        mModelV2 = new HomePageModelImplV2((Context) mView);
    }
    //没鸟用的方法
    @Override
    public void start() {}
    //从Api获取
    @Override
    public void getDataFromApi(String api, final ArrayList<ImageInfo> data, final boolean isRefreshing) {
        //TODO:吃屎的代码.......................................
        final boolean isFirstLoad = mView.isTheFirstLoad();
        //设置之后再也不是第一次加载
        mView.setTheFirstLoadFlag(false);
        //ModelImplV1..从网络获取
        new HomePageModelImplV1().getData(api, new HomePageContract.Model.OnConnectFinishListener() {
            @Override
            public void onSuccess(ArrayList<ImageInfo> newData) {
                //如果是下拉刷新 清空之后 在填入
                if (isRefreshing)
                    data.clear();
                data.addAll(newData);
                mView.updateListView();
                mView.hideLoading();
                //向数据库内写入缓存数据
                mModelV2.updateDbCache(isRefreshing, newData);
            }
            @Override
            public void onFailure(Throwable e) {
                e.printStackTrace();
                mView.loadError();
                mView.hideLoading();
                //若是第一次打开应用 可以选择从本地缓存取..之后不能
                if (isFirstLoad) {
                    mView.showSelectDialog("连接失败", "是否使用系统缓存");
                } else {
//                    mView.showAlertDialog("连接失败", "是否重试？？");
                    mView.showToast("朋友、能不能先检查下网络???");
                }
                mView.resetPage();
            }
        });
    }
    //从数据库获取之前的缓存数据
    @Override
    public void getDataFromDB(final ArrayList<ImageInfo> data) {
        mView.showLoading();
        mModelV2.getData(null, new HomePageContract.Model.OnConnectFinishListener() {
            @Override
            public void onSuccess(ArrayList<ImageInfo> newDataFromDB) {
                data.addAll(newDataFromDB);
                mView.updateListView();
                mView.hideLoading();
                mView.showToast("已为您找到 " + newDataFromDB.size() + " 张缓存图片..");
            }
            @Override
            public void onFailure(Throwable e) {
                mView.hideLoading();
            }
        });
    }
}

package com.example.administrator.mvp_beautylistview.homePage.mvp.constract;

import android.media.Image;

import com.example.administrator.mvp_beautylistview.homePage.mvp.model.entity.bean.ImageInfo;
import com.example.administrator.mvp_beautylistview.util.mvp.BaseModel;
import com.example.administrator.mvp_beautylistview.util.mvp.BasePresenter;
import com.example.administrator.mvp_beautylistview.util.mvp.BaseView;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/12.
 */

public interface HomePageContract {
    /**
     *  Model层
     */
    interface Model extends BaseModel {
        /*由Presenter调用*/
        //得到数据
        void getData(String api, OnConnectFinishListener listener);
//        //更新数据库
        void updateDbCache(boolean isRefreshing, ArrayList<ImageInfo> newData);
        //得到数据后 回调的接口
        interface OnConnectFinishListener {
            void onSuccess(ArrayList<ImageInfo> data);
            void onFailure(Throwable e);
        }
    }
    /**
     *  View层
     */
    interface View extends BaseView {
        /*以下方法供Presenter调用*/
        //调用notifyDataChanged()
        void updateListView();
        //加载失败
        void loadError();
        //显示loading动画
        void showLoading();
        //关闭loading动画
        void hideLoading();
        //重新设置页码 page
        void resetPage();
        //弹出选择框
        void showSelectDialog(String title, String content);
        //弹出提示框
        void showAlertDialog(String title, String content);
        //改变第一次进入的flag
        void setTheFirstLoadFlag(boolean isTheFirst);
        //判断是不是第一次加载
        boolean isTheFirstLoad();
        //展示ListView的底栏
        void showSubView();
        //隐藏ListView的底栏
        void hideSubView();
        //弹出提示
        void showToast(String content);
    }
    /**
     *  Presenter层
     */
    interface Presenter extends BasePresenter {
        //从网络获取
        void getDataFromApi(String api, final ArrayList<ImageInfo> data, final boolean isRefreshing);
        //从数据库获取
        void getDataFromDB(ArrayList<ImageInfo> data);
    }
}


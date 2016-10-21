package com.example.administrator.mvp_beautylistview.homePage.mvp.model;

import android.util.Log;
import com.example.administrator.mvp_beautylistview.homePage.mvp.constract.HomePageContract;
import com.example.administrator.mvp_beautylistview.homePage.mvp.model.entity.bean.ImageInfo;
import com.example.administrator.mvp_beautylistview.homePage.mvp.model.entity.bean.ImagesResult;
import com.example.administrator.mvp_beautylistview.util.DensityUtil;
import com.example.administrator.mvp_beautylistview.util.OkHttpUtil;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 *  负责 连接接口
 *      解析json
 *          直接返回图片路径的集合
 */
public class HomePageModelImplV1 implements HomePageContract.Model {
    @Override
    public void getData(String api, final OnConnectFinishListener listener) {
        Observable.just(api)
                .map(new Func1<String, ArrayList<ImageInfo>>() {
                    @Override
                    public ArrayList<ImageInfo> call(String api) {
                        String jsonStr = null;
                        ImagesResult imageResult;
                        try {
                            jsonStr = OkHttpUtil.loadStringFromUrl(api);
                        } catch (IOException e) {e.printStackTrace();}
                        //解析json
                        imageResult = new Gson().fromJson(jsonStr, ImagesResult.class);
                        removeInvalidElement(imageResult.getResult());
                        return setImgScale(imageResult.getResult());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<ImageInfo>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                        e.printStackTrace();
                    }
                    @Override
                    public void onNext(ArrayList<ImageInfo> data) {
                        //回调回去
                        listener.onSuccess(data);
                    }
                });
    }
    private void removeInvalidElement(List<ImageInfo> result) {
        //由于最开始一个为null 故remove
        result.remove(0);
    }
    private ArrayList<ImageInfo> setImgScale(List<ImageInfo> result) {
        try {
            for (ImageInfo info : result) {
                int width = info.getWidth();
                int height = info.getHeight();
                //宽高比
                info.setScale(height * 1.0f / width);
                info.setPxScreenHeight((int)(info.getScale() * DensityUtil.PX_SCREEN_WIDTH));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(result);
     }
    //对当前ModelImpl而言是无用方法
    @Override
    public void updateDbCache(boolean isRefreshing, ArrayList<ImageInfo> newData) {}
}

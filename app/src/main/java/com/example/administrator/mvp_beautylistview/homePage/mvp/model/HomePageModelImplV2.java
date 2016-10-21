package com.example.administrator.mvp_beautylistview.homePage.mvp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.mvp_beautylistview.homePage.mvp.constract.HomePageContract;
import com.example.administrator.mvp_beautylistview.homePage.mvp.model.entity.bean.ImageInfo;

import java.net.DatagramSocketImplFactory;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import de.greenrobot.dao.test.DbTest;
import me.itangqi.greendao.Cache;
import me.itangqi.greendao.CacheDao;
import me.itangqi.greendao.DaoMaster;
import me.itangqi.greendao.DaoSession;

/**
 *  从GreenDao里面取数据
 *      默认设置数据库缓存70张图片
 */
//TODO:频繁的查询 频繁的insert 性能需要优化
public class HomePageModelImplV2 implements HomePageContract.Model {
    //最大缓存数
    private final static int MAX_CACHE_COUNT = 70;
    //db
    private SQLiteDatabase mDb;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public HomePageModelImplV2(Context context) {
        setupDatabase(context);
    }
    //初始化数据库
    private void setupDatabase(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "notes-db", null);
        mDb = helper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDb);
        mDaoSession = mDaoMaster.newSession();
    }
    //供Presenter获取数据
    @Override
    public void getData(String api, OnConnectFinishListener listener) {
        List<Cache> caches = getAllData();
        ArrayList<ImageInfo> imgCache = new ArrayList<>();
        //将Cache转换成ImageInfo
        for (Cache cache : caches) {
            imgCache.add(new ImageInfo()
                    .setImage(cache.getImgPath())
                    .setPxScreenHeight(cache.getPxScreenHeight()));
        }
        listener.onSuccess(imgCache);
    }
    //用于从api获取完数据后向数据库内写入数据
    @Override
    public void updateDbCache(boolean isRefreshing, ArrayList<ImageInfo> newData) {
        //如果是下拉刷新的话, 先全部删除
        if (isRefreshing)
            deleteAll();
        int dataAllCount = getAllData().size();
        if (dataAllCount == 0) {
            //不存在数据   全部插入
            //因为每次刷新都会调用deleteAll()所以肯定为要插入所有
            insertAll(newData);
        } else if (dataAllCount < MAX_CACHE_COUNT) {
            //缓存数据不够 继续插入数据
            insertMiss(newData);
        } else {
            //dataAllCount >= MAX_CACHE_COUNT 更新已有数据
            return;
        }
    }
    //全部删除
    private void deleteAll() {
        //得到所有
        List<Cache> caches = getAllData();
        //循环根据id 全部删除
        for (Cache cache : caches) {
            mDaoSession.getCacheDao().deleteByKey(cache.getId());
        }
    }
    //添加所有
    private void insertAll(ArrayList<ImageInfo> newData) {
        for (ImageInfo info : newData) {
            //全部插入
            mDaoSession.getCacheDao().insert(new Cache(info.getImage(), info.getPxScreenHeight()));
        }
    }
    //添加不够的部分
    private void insertMiss(ArrayList<ImageInfo> newData) {
        for (ImageInfo info : newData) {
            //先查找是否存在 (img网址是可以作为主键存在的, 具有唯一性)
            Cache findCache = mDaoSession.getCacheDao().queryBuilder().where(CacheDao.Properties.ImgPath.eq(info.getImage())).unique();
            //当不存在数据时插入数据
            if (findCache == null) {
                Cache newCache = new Cache(info.getImage(), info.getPxScreenHeight());
                mDaoSession.getCacheDao().insert(newCache);
                //够数了 就不在增加
                if (getAllData().size() == MAX_CACHE_COUNT)
                    break;
            }//if
        }//for
    }
    //得到所有数据
    private List<Cache> getAllData() {
        return mDaoSession.getCacheDao().queryBuilder().build().list();
    }
    //将已有数据进行更新
    private void updateData(ArrayList<ImageInfo> newData) {
        List<Cache> caches = getAllData();
        //因为newData最多也就10个(小于等于MAX_CACHE_COUNT)所以用它做循环条件
        for ( int i = 0; i < newData.size(); i ++ ) {
            Cache cache = caches.get(i);
            ImageInfo info = newData.get(i);
            cache.setImgPath(info.getImage());
            cache.setPxScreenHeight(info.getPxScreenHeight());
            //数据库更新操作
            mDaoSession.getCacheDao().update(cache);
        }
    }

}

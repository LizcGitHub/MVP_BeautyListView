package com.example.administrator.mvp_beautylistview.myDownload.contract;

import com.example.administrator.mvp_beautylistview.util.mvp.BaseModel;
import com.example.administrator.mvp_beautylistview.util.mvp.BasePresenter;
import com.example.administrator.mvp_beautylistview.util.mvp.BaseView;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/16.
 */

public interface MyDownloadContract {
    interface Model extends BaseModel {
        void onComplete(ConnectDataListener listener);
        interface ConnectDataListener {
            void onSuccess(ArrayList<String> newData);
            void onFailure(Throwable e);
        }
    }
    interface View extends BaseView {

    }
    interface Presenter extends BasePresenter {

    }
}

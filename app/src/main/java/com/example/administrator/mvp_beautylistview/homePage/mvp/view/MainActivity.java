package com.example.administrator.mvp_beautylistview.homePage.mvp.view;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.administrator.mvp_beautylistview.R;
import com.example.administrator.mvp_beautylistview.homePage.mvp.constract.HomePageContract;
import com.example.administrator.mvp_beautylistview.homePage.mvp.model.entity.bean.ImageInfo;
import com.example.administrator.mvp_beautylistview.homePage.mvp.presenter.HomePagePresenterImplV1;
import com.example.administrator.mvp_beautylistview.homePage.mvp.view.Adapter.MyAdapter;
import com.example.administrator.mvp_beautylistview.util.DensityUtil;
import com.example.administrator.mvp_beautylistview.util.DialogUtil;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
public class MainActivity extends AppCompatActivity implements HomePageContract.View, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {
    @BindView(R.id.id_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.id_listview)
    ListView mListView;
    //ListView的数据
    private ArrayList<ImageInfo> mData;
    private BaseAdapter mAdapter;
    private HomePageContract.Presenter mPresenter;
    //页码
    private int sPage = 0;
    //一个tag
    private Object mTag = new Object();
    //第一次载入
    private boolean mIsFirstLoad = true;
    //Toast的时长
    private final static int TOAST_TIME = Toast.LENGTH_SHORT;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        init();
    }
    private void initView() {
        setWindowTheme();
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setColorSchemeColors(Color.argb(255, 195, 24, 220));
        mListView.setOnScrollListener(this);
    }
    private void setWindowTheme() {
        //全透明 沉浸式状态栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
    private void init() {
        //TODO:这句需要优化一下~~再说~~
        DensityUtil.init(this);
        mPresenter = new HomePagePresenterImplV1(this);
        mData = new ArrayList<>();
        mAdapter = new MyAdapter(this, mData);
        mListView.setAdapter(mAdapter);
        theFirstLoding();
    }
    //刚打开程序时加载数据
    private void theFirstLoding() {
        //第一次加载
        mSwipeRefresh.setRefreshing(true);
        mPresenter.getDataFromApi(getNewApi(true), mData, true);
    }
    @Override
    public void onRefresh() {
        mPresenter.getDataFromApi(getNewApi(true), mData, true);
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE :
                if(mListView.getLastVisiblePosition() == (mListView.getCount() - 1)) {
                    mPresenter.getDataFromApi(getNewApi(false), mData, false);
                }
                break;
        }
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            Picasso.with(this).resumeTag(mTag);
        } else {
            Picasso.with(getBaseContext()).pauseTag(mTag);
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}
    private String getNewApi(boolean isRefreshing) {
        //设置页码
        if (isRefreshing)
            sPage = 0;
        else sPage ++;
        //设置api
        return "http://mb.yidianzixun.com/api/q/?path=channel|news-list-for-channel&channel_id=u241&fields=docid&fields=category&fields=date&fields=image&fields=image_urls&fields=like&fields=source&fields=title&fields=url&fields=comment_count&fields=summary&fields=up" +
                        "&cstart=" + sPage * 10 +
                        "&cend=" + (sPage + 1) * 10 +
                        "&version=999999&infinite=true";
    }
    /**
     *  View层
     */
    @Override
    public void updateListView() {
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void loadError() {
//        Toast.makeText(this, "出异常了！！你知道吗！！\n\t你是不是没开网络！！！！", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showLoading() {
        if ( ! mSwipeRefresh.isRefreshing())
            mSwipeRefresh.setRefreshing(true);
    }
    @Override
    public void hideLoading() {
        if (mSwipeRefresh.isRefreshing())
            mSwipeRefresh.setRefreshing(false);
    }
    //当获取数据失败时 归位page
    @Override
    public void resetPage() {
        if (sPage > 0)
            sPage--;
    }
    @Override
    public void showSelectDialog(String title, String content) {
        DialogUtil.showAlertDialog(this, title, content, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //若是第一次加载
//                if (mIsFirstLoad) {
                    //从数据库当中取数据
                    mPresenter.getDataFromDB(mData);
//                    Log.d("这里取数据...", mData.toString());
//                }
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
    @Override
    public void showAlertDialog(String title, String content) {
        //TODO:提示框优化下
//        DialogUtil.showAlertDialog(this, title, content, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //TODO:可以改为判断 刷新和 更多
//                mSwipeRefresh.setRefreshing(true);
//                onRefresh();
//            }
//        }, null);
    }
    @Override
    public void setTheFirstLoadFlag(boolean isTheFirst) {
        mIsFirstLoad = isTheFirst;
    }
    @Override
    public boolean isTheFirstLoad() {
        return mIsFirstLoad;
    }
    @Override
    public void showSubView() {}
    @Override
    public void hideSubView() {}
    @Override
    public void showToast(String content) {
        Toast.makeText(this, content, TOAST_TIME).show();
    }
}

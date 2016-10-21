package com.example.administrator.mvp_beautylistview.util;

import android.content.Context;
import android.util.DisplayMetrics;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/6/25.
 */
public class DensityUtil {
    private static Context context = null;
    private static float DENSITY = 0.0f;
    private static DisplayMetrics metrics = null;
    public static int DP_SCREEN_WIDTH = 0;
    public static int PX_SCREEN_WIDTH = 0;
    public static void init(Context mContext) {
        context = mContext;
        metrics = context.getResources().getDisplayMetrics();
        DENSITY = metrics.density;
        PX_SCREEN_WIDTH = metrics.widthPixels;
        DP_SCREEN_WIDTH = px2dipByInt(PX_SCREEN_WIDTH);
    }
    public static int px2dipByInt(int pxValue) {
        return (int) (pxValue / DENSITY + 0.5f);
    }
    public static Float px2dipByFLoat(int pxValue) {
        return Float.valueOf(pxValue / DENSITY + 0.5f);
    }
    /*float 类型 返回两位小数*/
    public static float floatFormat(float value) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        return Float.valueOf(decimalFormat.format(value));
    }
}

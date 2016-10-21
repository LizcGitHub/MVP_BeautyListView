package com.example.administrator.mvp_beautylistview.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Administrator on 2016/10/15.
 */

public class DialogUtil {
    public static void showAlertDialog(Context context, String title, String msg, DialogInterface.OnClickListener positionListener, DialogInterface.OnClickListener negativeListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("好的", positionListener)
                .setNegativeButton("取消", negativeListener)
        .show();
    }
}

package com.mnnyang.tallybook.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by mnnyang on 17-5-17.
 */

public class SnackbarUtils {

    public static void notice(View view, String msg) {
        Snackbar.make(view, " ( ゜- ゜)つロ" + msg,
                Snackbar.LENGTH_SHORT).setAction("知道了", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

    public static void noticeAction(View view, String msg, String actionName, final View.OnClickListener clickListener) {
        Snackbar.make(view, " ( ゜- ゜)つロ" + msg,
                Snackbar.LENGTH_SHORT).setAction(actionName, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v);
            }
        }).show();
    }
}

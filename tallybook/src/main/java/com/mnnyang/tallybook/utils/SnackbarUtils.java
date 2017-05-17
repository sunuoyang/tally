package com.mnnyang.tallybook.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by mnnyang on 17-5-17.
 */

public class SnackbarUtils {

    public static void notice(View view, String mag) {
        Snackbar.make(view, " ( ゜- ゜)つロ" + mag,
                Snackbar.LENGTH_SHORT).setAction("知道了", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }
}

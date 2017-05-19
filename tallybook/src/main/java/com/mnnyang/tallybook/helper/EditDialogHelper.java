package com.mnnyang.tallybook.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mnnyang.tallybook.R;

/**
 * 输入框弹窗
 * Created by mnnyang on 17-5-18.
 */

public class EditDialogHelper {
    public void show(final Context context, String title, final ButtonListener listener) {
        final View editViewRoot = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null);
        final EditText editText = (EditText) editViewRoot.findViewById(R.id.et_input);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(editViewRoot);
        builder.setPositiveButton(context.getString(R.string.affirm),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onPositive(editViewRoot, editText.getText().toString());
                    }
                });
        builder.setNegativeButton(context.getString(R.string.canccel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onNegative();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                showKeyboard(context, editText);
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                listener.onDismiss();
            }
        });
        alertDialog.show();

    }

    private void showKeyboard(Context context, final EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
//        mHandler.postDelayed(new Runnable() {
//            public void run() {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
//            }
//        }, 200L);
    }

    public static abstract class ButtonListener {
        public abstract void onPositive(View editViewRoot, String content);

        public void onNegative(){};

        public void onDismiss(){}
    }
}

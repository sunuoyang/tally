package com.mnnyang.tallybook.helper;

import android.text.TextUtils;
import android.widget.EditText;

import com.mnnyang.tallybook.utils.LogUtils;

/**
 * <p>金额输入助手</p>
 * <p>规则:</p>
 * 1. 只能有一个小数点 <
 * 2. 小数位只能有1位 444.4 433.2
 * 3. 最大金额为999999999 长度为maxLength <
 * 4. 一开始不能输入0后输入其他数字 比如02 04 00 <
 * 5. 一开始不能输入小数点...<
 * 6.
 * <p>
 * Created by mnnyang on 17-5-17.
 */

public class MoneyEditHelper {

    private EditText editText;
    private String moneyText = "";
    private int maxLength = 9;

    public MoneyEditHelper(EditText editText) {
        this.editText = editText;
    }

    public void onAddNumber(String number) {
        //最大长度不能大于maxLength
        if (moneyText.length() >= maxLength) {
            return;
        }

        if (number.equals(".")) {
            isPoint();
        } else {
            isNumber(number);
        }
    }

    private void isNumber(String number) {
        //只输入了一个0, 禁止再输入数字
        if (moneyText.equals("0")) {
            return;
        }
        //只能有一位小数
        if (moneyText.matches("[0-9]+\\.[0-9]")) {
            System.out.println(moneyText + "  已经有一位小数");
            return;
        }
        moneyText += number;
        editText.setText(moneyText);
    }

    private void isPoint() {
        //不能一开始输入小数点
        if (TextUtils.isEmpty(moneyText)) {
            return;
        }
        //不能输入多个小数点
        if (moneyText.contains(".")) {
            return;
        }
        moneyText += ".";
        editText.setText(moneyText);
    }

    public void onDelete() {
        if (moneyText.length() > 0) {
            moneyText = moneyText.substring(0, moneyText.length() - 1);
        }
        editText.setText(moneyText);
    }


    private float getMoney() {
        String moneyString = editText.getText().toString();
        return getFloat(moneyString);
    }

    private float getFloat(String str) {

        float moneyFloat = 0;
        try {
            moneyFloat = Float.parseFloat(str);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(this, "string to float fail !");
        }
        return moneyFloat;
    }

}

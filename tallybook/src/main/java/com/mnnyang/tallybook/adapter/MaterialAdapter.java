package com.mnnyang.tallybook.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.app.app;
import com.mnnyang.tallybook.model.Bill;

import java.util.List;
import java.util.Random;

/**
 * Created by mnnyang on 17-5-21.
 */

public class MaterialAdapter extends RecyclerBaseAdapter<Bill> {

    private String date;
    private String year;
    private String month;
    private String day;

    public MaterialAdapter(@LayoutRes int itemLayoutId, @NonNull List<Bill> data) {
        super(itemLayoutId, data);
    }

    @Override
    protected void convert(ViewHolder holder, int position) {
        Bill bill = getData().get(position);
        date = bill.getDate() + "";
        year = date.substring(0, 4);
        month = date.substring(4, 6);
        day = date.substring(6, 8);

        holder.setText(R.id.tv_title, bill.getTitle());
        holder.setText(R.id.tv_money, bill.getMoney() + " 元");
        holder.setText(R.id.tv_date, month + "月" + day + "日");
        holder.getView(R.id.iv_notes).setVisibility(TextUtils.isEmpty(bill.getNotes()) ? View.INVISIBLE : View.VISIBLE);

        setIcon(holder, bill, position);
    }

    private void setIcon(ViewHolder holder, Bill bill, int position) {
        ImageView ivImage = holder.getView(R.id.iv_type_icon);
        String mainType = bill.getMainType();
        String minorType = bill.getMinorType();

        if (mainType.equals(app.context.getString(R.string.income))) {
            switchIncome(ivImage, minorType);
        } else {
            switchExpend(ivImage, minorType);
        }
    }

    private void switchExpend(ImageView imageView, String minor) {
        int resId;
        int resColor;
        switch (minor) {
            case "购物":
                resId = R.drawable.ic_shoping;
                break;
            case "餐饮":
                resId = R.drawable.ic_eat;
                break;
            case "出行":
                resId = R.drawable.ic_car;
                break;
            case "自定义":
                resId = R.drawable.ic_custom;
                break;
            case "娱乐":
                resId = R.drawable.ic_game;
                break;
            case "零食":
                resId = R.drawable.ic_snacks;
                break;
            case "烟酒":
                resId = R.drawable.ic_tob_wine;
                break;
            case "借出":
                resId = R.drawable.ic_lend;
                break;
            case "住房":
                resId = R.drawable.ic_home;
                break;
            case "旅游":
                resId = R.drawable.ic_travel;
                break;
            case "通讯":
                resId = R.drawable.ic_comm;
                break;
            case "医疗":
                resId = R.drawable.ic_medical;
                break;
            case "美容":
                resId = R.drawable.ic_beauty;
                break;
            case "日用品":
                resId = R.drawable.ic_daily;
                break;
            case "学习":
                resId = R.drawable.ic_study;
                break;
            case "人情":
                resId = R.drawable.ic_feelings;
                break;
            case "还债":
                resId = R.drawable.ic_debit;
                break;
            case "数码":
                resId = R.drawable.ic_digital;
                break;
            case "家居":
                resId = R.drawable.ic_jiaju;
                break;
            case "早餐":
                resId = R.drawable.ic_breakfast;
                break;
            case "午饭":
            case "晚饭":
                resId = R.drawable.ic_dining_normal;
                break;
            case "宵夜":
                resId = R.drawable.ic_night_snack;
                break;
            case "轮渡":
                resId = R.drawable.ic_ferry;
                break;
            case "航班":
                resId = R.drawable.ic_flight;
                break;
            case "公交":
                resId = R.drawable.ic_bus;
                break;
            case "打车":
                resId = R.drawable.ic_car;
                break;
            case "加油":
                resId = R.drawable.ic_refuel;
                break;
            default:
                resId = R.drawable.ic_custom;
                break;
        }
        imageView.setImageResource(resId);

        int rand = new Random().nextInt(10);
        imageView.setColorFilter(Color.WHITE);
        GradientDrawable drawable = (GradientDrawable) imageView.getBackground();
        drawable.setColor(app.context.getResources().getColor(R.color.colorAccent));
    }

    private void switchIncome(ImageView imageView, String minor) {
        int resId;
        switch (minor) {
            case "薪资":
                resId = R.drawable.ic_salary;
                break;
            case "红包":
                resId = R.drawable.ic_red_packet;
                break;
            case "奖金":
                resId = R.drawable.ic_bonus;
                break;
            case "兼职":
                resId = R.drawable.ic_part_time_jab;
                break;
            case "借入":
                resId = R.drawable.ic_borrow;
                break;
            case "自定义":
            default:
                resId = R.drawable.ic_custom;
                break;
        }

        imageView.setImageResource(resId);

        int rand = new Random().nextInt(10);
        imageView.setColorFilter(Color.WHITE);
        GradientDrawable drawable = (GradientDrawable) imageView.getBackground();
        drawable.setColor(app.context.getResources().getColor(R.color.blue));

    }
}

package com.mnnyang.tallybook.adapter;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.adapter.base.RecyclerBaseAdapter;
import com.mnnyang.tallybook.app.Cache;
import com.mnnyang.tallybook.app.app;
import com.mnnyang.tallybook.model.Bill;

import java.util.List;
import java.util.Random;

/**
 * 时间线recyclerview适配器
 * Created by mnnyang on 17-5-19.
 */

public class TimeLineAdapter extends RecyclerBaseAdapter<Bill> {
    public TimeLineAdapter(@LayoutRes int itemLayoutId, @NonNull List<Bill> data) {
        super(itemLayoutId, data);
    }

    @Override
    protected void convert(ViewHolder holder, int position) {
        setTimeLime(holder, position);
        Bill bill = getData().get(position);
        int date = bill.getDate();
        String day = (date + "").substring(6, 8);
        TextView tvMoney = holder.getView(R.id.tv_money);

        holder.setText(R.id.tv_time_line_title, bill.getTitle());
        holder.setText(R.id.tv_day, day);
        holder.setText(R.id.tv_money, bill.getMoney() + "");

        setMoney(bill, tvMoney);

        setIcon(holder, position);

        setYearMonth(holder, position, bill);
    }

    @Override
    protected void setItemEvent(final ViewHolder holder) {
        holder.getView(R.id.ll_item_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, holder);
                }
            }
        });
        holder.getView(R.id.ll_item_root).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemLongClick(v, holder);
                }
                return true;
            }
        });
    }

    private void setMoney(Bill bill, TextView tvMoney) {
        tvMoney.setTextColor(bill.getMainType().equals(app.context.getString(R.string.income)) ?
                app.context.getResources().getColor(R.color.colorPrimary) :
                app.context.getResources().getColor(R.color.colorAccent));
    }

    private void setYearMonth(ViewHolder holder, int position, Bill bill) {
        LinearLayout llYearMonth = holder.getView(R.id.ll_time_line_year_month);
        TextView textView = holder.getView(R.id.tv_time_line_date);
        String year = (bill.getDate() + "").substring(0, 4);//20171212
        String month = (bill.getDate() + "").substring(4, 6);//20171212


        if (position == 0) {
            llYearMonth.setVisibility(View.VISIBLE);
            setYearMonth(textView, year, month);
        } else {
            String lostYearMonth = (getData().get(position - 1).getDate() + "").substring(0, 6);
            if ((year + month).equals(lostYearMonth)) {
                llYearMonth.setVisibility(View.GONE);
            } else {
                llYearMonth.setVisibility(View.VISIBLE);
                setYearMonth(textView, year, month);
            }
        }
    }

    private void setYearMonth(TextView textView, String year, String month) {
        int monthInt = Integer.parseInt(month);
        if (monthInt < 1 || monthInt > 12) {
            throw new RuntimeException("adapter setYearMonth month error");
        }
        textView.setText(year + "  " + Cache.monthHan[monthInt]);
    }

    private void setIcon(ViewHolder holder, int position) {
        ImageView ivImage = holder.getView(R.id.iv_time_line_image);
        Bill bill = getData().get(position);
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
        imageView.setColorFilter(Cache.tintColorsExpend[rand]);
        GradientDrawable drawable = (GradientDrawable) imageView.getBackground();
        drawable.setStroke(3, Cache.tintColorsExpend[rand]);
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
        imageView.setColorFilter(Cache.tintColorsExpend[rand]);
        GradientDrawable drawable = (GradientDrawable) imageView.getBackground();
        drawable.setStroke(3, Cache.tintColorsExpend[rand]);
    }

    private void setTimeLime(ViewHolder holder, int position) {
        if (getItemCount() == 1) {
            hideLineHeader(holder);
            hideLineFooter(holder);
            goneTimeItem(holder);
        } else {
            if (position == getItemCount() - 1) {
                hideLineFooter(holder);
            } else {
                showLimeFooter(holder);
            }
        }
    }

    private void showLimeFooter(ViewHolder holder) {
        holder.getView(R.id.v_time_line_footer).setVisibility(View.VISIBLE);
    }

    private void showLineHeader(ViewHolder holder) {
        holder.getView(R.id.v_time_line_header).setVisibility(View.VISIBLE);
    }

    private void hideLineFooter(ViewHolder holder) {
        holder.getView(R.id.v_time_line_footer).setVisibility(View.INVISIBLE);
    }

    private void hideLineHeader(ViewHolder holder) {
        holder.getView(R.id.v_time_line_header).setVisibility(View.INVISIBLE);
    }

    private void showTimeItem(ViewHolder holder) {
        holder.getView(R.id.ll_time_line_year_month).setVisibility(View.VISIBLE);
    }

    private void goneTimeItem(ViewHolder holder) {
        holder.getView(R.id.ll_time_line_year_month).setVisibility(View.GONE);
    }
}

package com.mnnyang.tallybook.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.model.Bill;

import java.util.List;

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
        holder.setText(R.id.tv_time_line_title, getData().get(position).getTitle());
        int date = getData().get(position).getDate();
        String day = (date + "").substring(6, 8);
        holder.setText(R.id.tv_day, day + "日");
        holder.setText(R.id.tv_money, "-"+getData().get(position).getMoney() + "");
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

            if (position % 5 == 0) {
                showTimeItem(holder);
            } else {
                goneTimeItem(holder);
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
        holder.getView(R.id.ll_time_line_time_item).setVisibility(View.VISIBLE);
    }

    private void goneTimeItem(ViewHolder holder) {
        holder.getView(R.id.ll_time_line_time_item).setVisibility(View.GONE);
    }
}

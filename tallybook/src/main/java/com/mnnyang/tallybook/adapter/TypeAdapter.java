package com.mnnyang.tallybook.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.adapter.base.RecyclerBaseAdapter;
import com.mnnyang.tallybook.model.MinorType;

import java.util.List;

/**
 * Created by mnnyang on 17-5-17.
 */

public class TypeAdapter extends RecyclerBaseAdapter<MinorType> {
    public TypeAdapter(@LayoutRes int itemLayoutId, @NonNull List<MinorType> data) {
        super(itemLayoutId, data);
    }

    @Override
    protected void convert(ViewHolder holder, int position) {
        TextView tvTypeName = holder.getView(R.id.tv_type_name);
        ImageView ivTypeIcon = holder.getView(R.id.iv_type_icon);

        MinorType minorType = getData().get(position);
        int tintColor = minorType.getTintColor();
        tvTypeName.setText(minorType.getTypeName());
        ivTypeIcon.setImageResource(getData().get(position).getTypeIconId());

        if (tintColor != -1) {
            tvTypeName.setTextColor(tintColor);
            ivTypeIcon.setColorFilter(tintColor);
        }
    }

    @Override
    protected void setItemEvent(final ViewHolder holder) {
        holder.getView(R.id.ll_real_type_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v, holder);
            }
        });

        holder.getView(R.id.ll_real_type_item).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemClickListener.onItemLongClick(v, holder);
                return true;
            }
        });
    }
}

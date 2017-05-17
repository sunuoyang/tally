package com.mnnyang.tallybook.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.activity.AddActivity;
import com.mnnyang.tallybook.adapter.RecyclerBaseAdapter;
import com.mnnyang.tallybook.app.app;
import com.mnnyang.tallybook.fragment.base.BaseFragment;
import com.mnnyang.tallybook.model.EntryType;
import com.mnnyang.tallybook.utils.LogUtils;
import com.mnnyang.tallybook.utils.building.BindView;
import com.mnnyang.tallybook.utils.building.Binder;

import java.util.ArrayList;

/**
 * Created by mnnyang on 17-5-17.
 */

public class TypeSelectFragment extends BaseFragment implements View.OnTouchListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private int spanCount = 5;
    private float startMoveY;
    private boolean isMoving;
    private GridLayoutManager layoutManager;

    public TypeSelectFragment initType(String type) {
        this.type = type;
        return this;
    }

    private String type;


    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(app.context).inflate(R.layout.fragment_type_select, null);
        Binder.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        ArrayList<EntryType> entryTypes = getTypeData();

        layoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecyclerBaseAdapter<EntryType>(R.layout.item_type_select, entryTypes) {
            @Override
            protected void convert(ViewHolder holder, int position) {
                TextView tvTypeName = holder.getView(R.id.tv_type_name);
                ImageView ivTypeIcon = holder.getView(R.id.iv_type_icon);

                EntryType entryType = getData().get(position);
                int tintColor = entryType.getTintColor();
                tvTypeName.setText(entryType.getTypeName());
                ivTypeIcon.setImageResource(getData().get(position).getTypeIconId());

                if (tintColor != -1) {
                    tvTypeName.setTextColor(tintColor);
                    ivTypeIcon.setColorFilter(tintColor);
                }
            }
        });
        recyclerView.setOnTouchListener(this);
    }

    /**
     * 返回账单的类型
     */
    private ArrayList<EntryType> getTypeData() {
        if (type.equals(getString(R.string.income))) {
            return initIncomeTypeData();
        } else if (type.equals(getString(R.string.expend))) {
            return initExpendTypeData();
        } else {
            LogUtils.e(this, "not find type!");
            return null;
        }
    }

    //TODO 完善类型
    @NonNull
    private ArrayList<EntryType> initExpendTypeData() {
        return initIncomeTypeData();
    }

    //TODO 完善类型
    @NonNull
    private ArrayList<EntryType> initIncomeTypeData() {
        ArrayList<EntryType> entryTypes = new ArrayList<>();
        entryTypes.add(new EntryType().setTypeIconId(R.drawable.ic_shop).setTypeName("购物")
                .setTintColor(app.context.getResources().getColor(R.color.yellow)));
        entryTypes.add(new EntryType().setTypeIconId(R.drawable.ic_eat).setTypeName("餐饮")
                .setTintColor(app.context.getResources().getColor(R.color.blue)));
        entryTypes.add(new EntryType().setTypeIconId(R.drawable.ic_game).setTypeName("娱乐")
                .setTintColor(app.context.getResources().getColor(R.color.pink)));
        entryTypes.add(new EntryType().setTypeIconId(R.drawable.ic_travel).setTypeName("旅游")
                .setTintColor(app.context.getResources().getColor(R.color.green)));
        entryTypes.add(new EntryType().setTypeIconId(R.drawable.ic_medical).setTypeName("医疗")
                .setTintColor(app.context.getResources().getColor(R.color.teal)));
        return entryTypes;
    }

    //recyclerview onTouch
    @Override
    public boolean onTouch(View v, MotionEvent e) {
        float y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!isMoving) {
                    isMoving = true;
                    startMoveY = y;
                }
                if (startMoveY - y > 20) {
                    hideNumberInputView();
                } else if (layoutManager.findFirstVisibleItemPosition() == 0 && y - startMoveY > 20) {
                    showNumberInputView();
                }
                break;
            default:
                System.out.println("def");
                startMoveY = 0;
                isMoving = false;
                break;
        }
        return false;
    }

    private void hideNumberInputView() {
        ((AddActivity) getActivity()).hideNumberInputView();
    }

    private void showNumberInputView() {
        ((AddActivity) getActivity()).showNumberInputView();
    }
}

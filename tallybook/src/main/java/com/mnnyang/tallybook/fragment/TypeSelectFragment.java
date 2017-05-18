package com.mnnyang.tallybook.fragment;

import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.activity.AddActivity;
import com.mnnyang.tallybook.adapter.RecyclerBaseAdapter;
import com.mnnyang.tallybook.adapter.TypeAdapter;
import com.mnnyang.tallybook.app.app;
import com.mnnyang.tallybook.db.EntryHelpter;
import com.mnnyang.tallybook.fragment.base.BaseFragment;
import com.mnnyang.tallybook.helper.ListDialogHelper;
import com.mnnyang.tallybook.helper.SpacesItemDecoration;
import com.mnnyang.tallybook.model.Bill;
import com.mnnyang.tallybook.model.MinorType;
import com.mnnyang.tallybook.utils.ArrayUtils;
import com.mnnyang.tallybook.utils.LogUtils;
import com.mnnyang.tallybook.utils.ScreenUtils;
import com.mnnyang.tallybook.utils.SnackbarUtils;
import com.mnnyang.tallybook.utils.building.BindView;
import com.mnnyang.tallybook.utils.building.Binder;

import java.util.ArrayList;

/**
 * Created by mnnyang on 17-5-17.
 */

public class TypeSelectFragment extends BaseFragment implements View.OnTouchListener, RecyclerBaseAdapter.ItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private int spanCount = 4;
    private float startMoveY;
    private boolean isMoving;
    private GridLayoutManager layoutManager;
    private TypeAdapter adapter;
    private ArrayList<MinorType> typeData;

    public TypeSelectFragment initType(String type) {
        this.mainType = type;
        return this;
    }

    private String mainType;

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
        typeData = getTypeData();

        layoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
        SpacesItemDecoration decoration = new SpacesItemDecoration(ScreenUtils.dp2px(8));
        recyclerView.addItemDecoration(decoration);
        adapter = new TypeAdapter(R.layout.item_type_select, typeData);
        recyclerView.setAdapter(adapter);

        recyclerView.setOnTouchListener(this);
        adapter.setItemClickListener(this);
    }

    /**
     * 返回账单的类型
     */
    private ArrayList<MinorType> getTypeData() {
        if (mainType.equals(getString(R.string.income))) {
            return initIncomeTypeData();
        } else if (mainType.equals(getString(R.string.expend))) {
            return initExpendTypeData();
        } else {
            LogUtils.e(this, "not find mainType!");
            throw new RuntimeException("not find mainType!");
        }
    }

    @NonNull
    private ArrayList<MinorType> initExpendTypeData() {
        ArrayList<MinorType> minorTypes = fillTypeData(R.array.minor_type_income,
                R.array.minor_type_drawable_ids_income, R.array.minor_type_tint_income);
        return minorTypes;
    }

    @NonNull
    private ArrayList<MinorType> initIncomeTypeData() {
        ArrayList<MinorType> minorTypes = fillTypeData(R.array.minor_type,
                R.array.minor_type_drawable_ids, R.array.minor_type_tint);
        return minorTypes;
    }

    @NonNull
    private ArrayList<MinorType> fillTypeData(@ArrayRes int typeTitles, @ArrayRes int typeDrawables, @ArrayRes int typeTint) {
        ArrayList<MinorType> minorTypes = new ArrayList<>();

        String monorTypeTitle[] = ArrayUtils.getStringArray(typeTitles);
        int iconsId[] = ArrayUtils.getIds(typeDrawables);
        int tintColors[] = ArrayUtils.getIntArray(typeTint);

        for (int i = 0; i < monorTypeTitle.length; i++) {
            minorTypes.add(new MinorType()
                    .setTypeName(monorTypeTitle[i])
                    .setTypeIconId(iconsId[i])
                    .setTintColor(tintColors[i]));
        }
        return minorTypes;
    }

    //recyclerview item click
    @Override
    public void onItemClick(View view, RecyclerBaseAdapter.ViewHolder holder) {
        //TODO 选择完毕 添加数据 返回主页
        float money = ((AddActivity) getActivity()).getShowMoney();
        if (money == 0f) {
            SnackbarUtils.notice(view, getString(R.string.set_the_money_value));
            return;
        }

        int date = ((AddActivity) getActivity()).getSelectDate();
        MinorType minorType = typeData.get(holder.getAdapterPosition());
        String notes = "";
        addBill(minorType, money, date, notes);
        System.out.println(money);
    }

    @Override
    public void onItemLongClick(View view, RecyclerBaseAdapter.ViewHolder holder) {
        //TODO 弹出选择窗口 选择则添加数据 返回主页
        showTypeDetailDialog();
    }

    /**
     * 详细类型弹窗
     */
    private void showTypeDetailDialog() {

        ArrayList<ListDialogHelper.Item> items = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            items.add(new ListDialogHelper.Item().setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).setTitle("nihoa"));
        }
        new ListDialogHelper().showListDialog(getContext(), items, new ListDialogHelper.ItemClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                System.out.println(item);
            }
        });
    }

    /**
     * 添加一条账单
     */
    private void addBill(MinorType type, float money, int date, String notes) {
        long addTime = System.currentTimeMillis();
        Bill bill = new Bill()
                .setTitle(type.getTypeName())
                .setMoney(money)
                .setDate(date)
                .setAddTime(addTime)
                .setMainType(mainType)
                .setMinorType(type.getTypeName())
                .setNotes(notes);

        EntryHelpter helpter = new EntryHelpter();
        helpter.add(bill);
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

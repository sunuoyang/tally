package com.mnnyang.tallybook.fragment;

import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.activity.AddActivity;
import com.mnnyang.tallybook.adapter.base.RecyclerBaseAdapter;
import com.mnnyang.tallybook.adapter.TypeAdapter;
import com.mnnyang.tallybook.app.app;
import com.mnnyang.tallybook.db.EntryHelpter;
import com.mnnyang.tallybook.fragment.base.BaseFragment;
import com.mnnyang.tallybook.helper.EditDialogHelper;
import com.mnnyang.tallybook.helper.ListDialogHelper;
import com.mnnyang.tallybook.helper.SpacesItemDecoration;
import com.mnnyang.tallybook.model.Bill;
import com.mnnyang.tallybook.model.MinorType;
import com.mnnyang.tallybook.utils.ArrayUtils;
import com.mnnyang.tallybook.utils.LogUtils;
import com.mnnyang.tallybook.utils.ScreenUtils;
import com.mnnyang.tallybook.utils.ToastUtils;
import com.mnnyang.tallybook.utils.building.BindView;
import com.mnnyang.tallybook.utils.building.Binder;

import java.util.ArrayList;

/**
 * 类型选择
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
        ArrayList<MinorType> minorTypes = fillTypeData(R.array.minor_type_expend,
                R.array.minor_type_drawable_ids_expend, R.array.minor_type_tint_expend);
        return minorTypes;
    }

    @NonNull
    private ArrayList<MinorType> initIncomeTypeData() {
        ArrayList<MinorType> minorTypes = fillTypeData(R.array.minor_type_income,
                R.array.minor_type_drawable_ids_income, R.array.minor_type_tint_income);
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
        boolean isInputValue = checkMoneyValue(view);
        if (!isInputValue) {
            return;
        }
        String typeName = typeData.get(holder.getAdapterPosition()).getTypeName();
        if (typeName.equals(getString(R.string.custom))) {
            customType(typeName);
            return;
        }
        addBillByType(typeName, typeName);
    }

    @Override
    public void onItemLongClick(View view, RecyclerBaseAdapter.ViewHolder holder) {
        MinorType minorType = typeData.get(holder.getAdapterPosition());
        if (minorType.getTypeName().equals(getString(R.string.trip))) {
            showTypeDetailDialog(view, minorType.getTypeName());
            return;
        }
        if (minorType.getTypeName().equals(getString(R.string.dining))) {
            showTypeDetailDialog(view, minorType.getTypeName());
            return;
        }
    }

    /**
     * 确定类型弹窗
     */
    private void showDetermineDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(view);
    }

    /**
     * 详细类型弹窗
     *
     * @param view
     * @param typeName
     */
    private void showTypeDetailDialog(final View view, final String typeName) {
        String[] titles = null;
        int[] iconIds = null;
        if (typeName.equals(getString(R.string.dining))) {
            titles = ArrayUtils.getStringArray(R.array.type_dining);
            iconIds = ArrayUtils.getIds(R.array.type_dining_drawable_ids);
        } else if (typeName.equals(getString(R.string.trip))) {
            titles = ArrayUtils.getStringArray(R.array.type_trip);
            iconIds = ArrayUtils.getIds(R.array.type_trip_drawable_ids);
        }

        ArrayList<ListDialogHelper.Item> dialogItems = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            dialogItems.add(new ListDialogHelper.Item()
                    .setBitmap(BitmapFactory.decodeResource(getResources(), iconIds[i]))
                    .setTitle(titles[i]));
        }

        final String[] finalTitles = titles;
        new ListDialogHelper().showListDialog(getContext(), dialogItems, new ListDialogHelper.ItemClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean isInputValue = checkMoneyValue(view);
                if (!isInputValue) {
                    return;
                }
                if (item == finalTitles.length - 1) {
                    customType(typeName);
                } else {
                    addBillByType(finalTitles[item], finalTitles[item]);
                }
            }
        });
    }

    /**
     * 自定义类型
     *
     * @param typeName
     */
    private void customType(final String typeName) {

        new EditDialogHelper().show(getContext(), getString(R.string.custom), new EditDialogHelper.ButtonListener() {
            @Override
            public void onPositive(View editViewRoot, String content) {
                if (TextUtils.isEmpty(content)) {
                    ((AddActivity) getActivity()).notice(getString(R.string.no_any_input));
                    return;
                }
                addBillByType(typeName, content);
            }
        });
    }

    /**
     * 根据类型添加
     */
    private void addBillByType(String type, String title) {
        AddActivity addActivity = (AddActivity) getActivity();
        float money = addActivity.getShowMoney();
        int date = addActivity.getSelectDate();
        String notes = addActivity.getNotes();
        addBill(title, type, money, date, notes);
        addFinishExit();
    }

    /**
     * 添加一条账单
     */
    private void addBill(String title, String type, float money, int date, String notes) {
        long addTime = System.currentTimeMillis();
        Bill bill = new Bill()
                .setMainType(mainType)
                .setAddTime(addTime)
                .setMinorType(type)
                .setTitle(title)
                .setMoney(money)
                .setNotes(notes)
                .setDate(date);

        EntryHelpter helpter = new EntryHelpter();
        helpter.add(bill);
    }

    /**
     * 检查是否输入金额
     */
    private boolean checkMoneyValue(View view) {
        AddActivity addActivity = (AddActivity) getActivity();

        //money 是否输入检查
        float money = addActivity.getShowMoney();
        if (money == 0f) {
            addActivity.notice(getString(R.string.set_the_money_value));
            return false;
        }
        return true;
    }

    /**
     * 添加完成退出
     */
    private void addFinishExit() {
        ToastUtils.show("添加成功!");
        getActivity().finish();
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

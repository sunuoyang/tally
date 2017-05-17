package com.mnnyang.tallybook.activity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.activity.base.BaseActivity;
import com.mnnyang.tallybook.adapter.FragmentAdapter;
import com.mnnyang.tallybook.fragment.TypeSelectFragment;
import com.mnnyang.tallybook.helper.MoneyEditHelper;
import com.mnnyang.tallybook.utils.ScreenUtils;
import com.mnnyang.tallybook.utils.building.BindLayout;
import com.mnnyang.tallybook.utils.building.BindView;
import com.mnnyang.tallybook.widget.NumberInputView;

@BindLayout(R.layout.activity_add)
public class AddActivity extends BaseActivity implements View.OnClickListener, NumberInputView.KeyboardListener {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.et_input)
    EditText etMoneyInput;

    @BindView(R.id.ll_input_root)
    LinearLayout llInputViewRoot;
    @BindView(R.id.niv_number_input)
    NumberInputView numberInputView;

    @BindView(R.id.tv_selsect_time)
    TextView tvSelectTime;


    private boolean isNumberInputViewHide = false;
    //金额输入检测助手
    private MoneyEditHelper moneyHelper;
    private DatePickerDialog mDatePickerDialog;


    @Override
    protected void initWindow() {
        ScreenUtils.transparentSystemBar(this);
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();
        initViewPager();
        initHelper();
        initDatePicker();
    }

    private void initDatePicker() {
        mDatePickerDialog = new DatePickerDialog(this, null, 2016, 1, 1);
    }

    private void initHelper() {
        moneyHelper = new MoneyEditHelper(etMoneyInput);
    }

    private void initViewPager() {
        String inCome = getString(R.string.income);
        String expend = getString(R.string.expend);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addFragment(new TypeSelectFragment().initType(inCome));
        fragmentAdapter.addFragment(new TypeSelectFragment().initType(expend));
        fragmentAdapter.setTitles(new String[]{expend, inCome});
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
            int height = ScreenUtils.getStatusBarHeight(this);
            lp.topMargin = height;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                /*getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));*/
            }
            toolbar.setLayoutParams(lp);
        }
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void initListener() {
        super.initListener();
        etMoneyInput.setOnClickListener(this);
        numberInputView.setKeyboardListener(this);
        tvSelectTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_input:
                showNumberInputView();
                break;
            case R.id.tv_selsect_time:
                selectTime();
                break;
            default:
                break;
        }
    }

    private void selectTime() {
        mDatePickerDialog.show();
    }

    @Override
    public void onNumber(String number) {
        moneyHelper.onAddNumber(number);
    }

    @Override
    public void onNumberDelete() {
        moneyHelper.onDelete();
    }

    /**
     * 显示数字输入键盘
     */
    public void showNumberInputView() {
        if (!isNumberInputViewHide) {
            return;
        }
        llInputViewRoot.animate().translationY(0).start();
        isNumberInputViewHide = false;
    }

    /**
     * 隐藏数字输入键盘
     */
    public void hideNumberInputView() {
        if (isNumberInputViewHide) {
            return;
        }
        llInputViewRoot.animate().translationY(llInputViewRoot.getHeight()).start();
        isNumberInputViewHide = true;
    }


}

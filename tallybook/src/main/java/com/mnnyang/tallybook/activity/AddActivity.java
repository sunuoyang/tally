package com.mnnyang.tallybook.activity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mnnyang.numberkeyboard.NumberInputView;
import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.activity.base.BaseActivity;
import com.mnnyang.tallybook.adapter.FragmentAdapter;
import com.mnnyang.tallybook.fragment.TypeSelectFragment;
import com.mnnyang.tallybook.helper.DateCheckHelper;
import com.mnnyang.tallybook.helper.EditDialogHelper;
import com.mnnyang.tallybook.helper.MoneyEditHelper;
import com.mnnyang.tallybook.utils.ScreenUtils;
import com.mnnyang.tallybook.utils.SnackbarUtils;
import com.mnnyang.tallybook.utils.TimeUtils;
import com.mnnyang.tallybook.utils.building.BindLayout;
import com.mnnyang.tallybook.utils.building.BindView;

import java.util.Calendar;

@BindLayout(R.layout.activity_add)
public class AddActivity extends BaseActivity implements View.OnClickListener, NumberInputView.KeyboardListener, DatePickerDialog.OnDateSetListener {

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

    private String entryNotes;
    private int entryYear;
    private int entryMonth;
    private int entryDay;
    private int entryDate;

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
        Calendar calendar = Calendar.getInstance();
        entryYear = calendar.get(Calendar.YEAR);
        entryMonth = calendar.get(Calendar.MONTH);
        entryDay = calendar.get(Calendar.DAY_OF_MONTH);
        String date = TimeUtils.stampToDate(calendar.getTimeInMillis(), "yyyyMMdd");
        entryDate = Integer.decode(date);

        mDatePickerDialog = new DatePickerDialog(this, this, entryYear, entryMonth, entryDay);
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
        llInputViewRoot.setOnClickListener(this);

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
            case R.id.ll_input_root:
                showNumberInputView();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_notes) {
            showNotesDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 添加备注对话框
     */
    private void showNotesDialog() {
        new EditDialogHelper().show(this, getString(R.string.notes), new EditDialogHelper.ButtonListener() {
            @Override
            public void onPositive(View editViewRoot, String content) {
                entryNotes = content;
                if (!TextUtils.isEmpty(entryNotes)) {
                    notice(getString(R.string.notes_set_succeed));
                }
            }
        });
    }

    /**
     * 选择日期
     */
    private void selectTime() {
        mDatePickerDialog.show();
    }

    /**
     * 日期控件回调
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        System.out.println("y" + year + "m" + month + "d" + dayOfMonth);

        new DateCheckHelper(year, month, dayOfMonth, new DateCheckHelper.Listener() {
            @Override
            public void succeed(int date, int year, int month, int dayOfMonth, String name) {
                selectTimeSucceed(date, year, month, dayOfMonth, name);
            }

            @Override
            public void fail() {
                selectTimeFail();
            }
        }).start();
    }

    /**
     * 选择日期正确
     */
    private void selectTimeSucceed(int date, int year, int month, int dayOfMonth, String name) {
        entryYear = year;
        entryMonth = month;
        entryDay = dayOfMonth;
        entryDate = date;

        if (!TextUtils.isEmpty(name)) {
            tvSelectTime.setText(name);
            return;
        }
        tvSelectTime.setText(year + "-" + month + "-" + dayOfMonth);
    }

    /**
     * 选择日期错误
     */
    private void selectTimeFail() {
        notice("不能选择未来的时间!");
    }

    //输入金额回调
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
        llInputViewRoot.animate().translationY(llInputViewRoot.getHeight() - ScreenUtils.dp2px(48)).start();
        isNumberInputViewHide = true;
    }

    /**
     * 显示通知
     *
     * @param msg
     */
    public void notice(String msg) {
        SnackbarUtils.notice(tvSelectTime, msg);
    }

    /**
     * 获取输入的金额
     */
    public float getShowMoney() {
        String string = etMoneyInput.getText().toString();
        if (TextUtils.isEmpty(string)) {
            return 0f;
        }
        return Float.parseFloat(string);
    }

    /**
     * 获取选择的日期
     */
    public int getSelectDate() {
        return entryDate;
    }

    /**
     * 获取设置的备注
     */
    public String getNotes() {
        return entryNotes;
    }


}

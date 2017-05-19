package com.mnnyang.tallybook.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.activity.base.BaseActivity;
import com.mnnyang.tallybook.adapter.TimeLineAdapter;
import com.mnnyang.tallybook.db.EntryHelpter;
import com.mnnyang.tallybook.model.Bill;
import com.mnnyang.tallybook.utils.ScreenUtils;
import com.mnnyang.tallybook.utils.building.BindLayout;
import com.mnnyang.tallybook.utils.building.BindView;

import java.util.ArrayList;
import java.util.Calendar;

@BindLayout(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ctl)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.tv_expend)
    TextView tvShowExpend;
    @BindView(R.id.tv_income)
    TextView tvShowIncome;
    @BindView(R.id.tv_budget)
    TextView tvShowBudgetOverspend;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ArrayList<Bill> billsList = new ArrayList<>();
    private TimeLineAdapter timeLineAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void initWindow() {
        ScreenUtils.transparentSystemBar(this);
    }

    @Override
    protected void initView() {
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH);
            actionBar.setTitle(month + "月");
        }
    }

    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        timeLineAdapter = new TimeLineAdapter(R.layout.item_time_line, billsList);
        recyclerView.setAdapter(timeLineAdapter);
    }

    @Override
    protected void initListener() {
        tvShowBudgetOverspend.setOnClickListener(this);
        fab.setOnClickListener(this);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(1) &&
                        linearLayoutManager.findLastVisibleItemPosition() == timeLineAdapter.getItemCount() - 1) {
                    fab.hide();
                }
                if (!fab.isShown()) {
                    fab.show();
                }
            }
        });
    }

    @Override
    protected void initData() {
        ArrayList<Bill> bills = new EntryHelpter().queryAll();
        billsList.addAll(bills);
        /*
        billsList.add(new Bill().setTitle("转角路喝酒"));
        billsList.add(new Bill().setTitle("买花"));
        billsList.add(new Bill().setTitle("购物"));
        billsList.add(new Bill().setTitle("打车"));
        billsList.add(new Bill().setTitle("到处乱转"));
        billsList.add(new Bill().setTitle("我曹"));
        billsList.add(new Bill().setTitle("钱丢了"));
       billsList.add(new Bill().setTitle("转角路喝酒"));
        billsList.add(new Bill().setTitle("转角路喝酒"));
        billsList.add(new Bill().setTitle("转角路喝酒"));
        billsList.add(new Bill().setTitle("转角路喝酒"));
        billsList.add(new Bill().setTitle("转角路喝酒"));
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_personal:
                personalClick();
                break;
            case R.id.action_search:
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                fabClick();
                break;
            case R.id.tv_budget:
                budgetClick();
                break;
            default:
                break;
        }
    }

    /**
     * 修改预算
     */
    private void budgetClick() {

    }


    private void fabClick() {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent);
    }

    private void personalClick() {
        Intent intent = new Intent(MainActivity.this, LoginActivityDemo.class);
        startActivity(intent);
    }
}

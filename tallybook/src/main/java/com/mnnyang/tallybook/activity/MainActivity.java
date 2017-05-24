package com.mnnyang.tallybook.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mnnyang.tallybook.AppBarStateChangeListener;
import com.mnnyang.tallybook.R;
import com.mnnyang.tallybook.activity.base.BaseActivity;
import com.mnnyang.tallybook.adapter.MaterialAdapter;
import com.mnnyang.tallybook.adapter.TimeLineAdapter;
import com.mnnyang.tallybook.adapter.base.RecyclerBaseAdapter;
import com.mnnyang.tallybook.db.EntryHelpter;
import com.mnnyang.tallybook.model.Bill;
import com.mnnyang.tallybook.utils.ScreenUtils;
import com.mnnyang.tallybook.utils.SnackbarUtils;
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
    @BindView(R.id.appbar_layout)
    AppBarLayout appBarLayout;
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
    private RecyclerBaseAdapter itemAdapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean isTimeLineTheme;
    private Bill deletebill;
    private int deleteItem;

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
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        selectAdapter();
    }

    private void selectAdapter() {
        isTimeLineTheme = getIsTimeLime();
        if (isTimeLineTheme) {
            itemAdapter = new TimeLineAdapter(R.layout.item_time_line, billsList);
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.set(0, 0, 0, 0);
                }
            });

        } else {
            itemAdapter = new MaterialAdapter(R.layout.item_material, billsList);
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.set(0, ScreenUtils.dp2px(8), 0, 0);
                }

            });
        }

        setItemClickListener(itemAdapter);
        itemAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(itemAdapter);
    }


    private boolean getIsTimeLime() {
        String configFileName = getPackageName() + "_preferences";
        String key = getString(R.string.key_time_line_theme);

        boolean result = getSharedPreferences(configFileName, Context.MODE_PRIVATE)
                .getBoolean(key, false);
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        applySet();
    }


    private void applySet() {
        boolean isTimeLimeTheme = getIsTimeLime();
        if (isTimeLimeTheme != this.isTimeLineTheme) {
            recreate();
        }
    }

    @Override
    protected void initListener() {
        tvShowBudgetOverspend.setOnClickListener(this);
        fab.setOnClickListener(this);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    fab.show();
                } else if (state == State.COLLAPSED) {
                    fab.hide();
                }
            }
        });
    }

    private void setItemClickListener(RecyclerBaseAdapter adapter) {
        adapter.setItemClickListener(new RecyclerBaseAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerBaseAdapter.ViewHolder holder) {
                itemClick(holder.getAdapterPosition());
            }

            @Override
            public void onItemLongClick(View view, RecyclerBaseAdapter.ViewHolder holder) {
                itemLongClick(holder.getAdapterPosition());
            }
        });
    }

    private void itemLongClick(final int position) {
        Bill bill = billsList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确定删除吗？");
        builder.setMessage(bill.getTitle() + "  (" + bill.getMainType() + bill.getMoney() + "元)");
        builder.setPositiveButton(getString(R.string.affirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemDelete(position);
            }
        });
        builder.setNegativeButton(getString(R.string.canccel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    private void itemClick(int position) {
        System.out.println(billsList.get(position).getTitle());
    }

    private void itemDelete(int position) {
        deleteItem = position;
        deletebill = billsList.get(position);

        billsList.remove(position);
        itemAdapter.notifyItemRemoved(position);
        showUndoSnakebar();
    }

    private void showUndoSnakebar() {
        SnackbarUtils.noticeAction(fab, "删除成功!", "撤销", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billsList.add(deleteItem, deletebill);
                itemAdapter.notifyItemInserted(deleteItem);
            }
        });
    }

    @Override
    protected void initData() {
        loadBillsData();
    }

    private void loadBillsData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Bill> bills = new EntryHelpter().queryAll();
                billsList.addAll(bills);
                itemAdapter.notifyDataSetChanged();
            }
        }).start();
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
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

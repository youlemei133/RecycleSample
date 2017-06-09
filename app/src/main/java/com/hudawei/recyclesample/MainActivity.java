package com.hudawei.recyclesample;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hudawei.recyclesample.level.LevelAdapter;
import com.hudawei.recyclesample.level.LevelBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int[] colors = {R.color.colorAccent, R.color.colorPrimary, R.color.colorGreen, R.color.colorBlue};
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TableView tableView = (TableView) findViewById(R.id.tableView);
//        tableView.setAdapter(new MyAdapter());
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        Log.e("MainActivity","widthPixels : "+metrics.widthPixels + "\theightPixels : " +metrics.heightPixels);

        findView();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new LevelAdapter(this, getDatas()));

        /**
         *  a.第一个可见View是否为打开状态
         *  b.第一个完全可见View是否属于
         */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                View view = manager.findViewByPosition(mCurrentPosition);
                int firstPosition = manager.findFirstVisibleItemPosition();
                LevelBean bean = mDatas.get(firstPosition);
                if (bean.getState() == LevelBean.STATE_OPEN && bean.getParent() == null) {

                } else {

                }

            }
        });


    }

    int mCurrentPosition;
    ImageView iv_circle;
    TextView tv_name;
    TextView tv_num;
    RelativeLayout rv_main;

    private void findView() {
        iv_circle = (ImageView) findViewById(R.id.iv_circle);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_num = (TextView) findViewById(R.id.tv_num);
        rv_main = (RelativeLayout) findViewById(R.id.rv_main);
    }

    private void bindView(LevelBean levelBean, int position) {
        tv_name.setText(levelBean.getName());
        tv_num.setText(levelBean.getNum() + "道");
        rv_main.setBackgroundColor(ContextCompat.getColor(this, levelBean.getColor()));

        iv_circle.setTag(R.id.level_item_position, position);

        switch (levelBean.getState()) {
            case LevelBean.STATE_CLOSE:
                iv_circle.setImageResource(R.drawable.ic_add_circle_black_24dp);
                break;
            case LevelBean.STATE_OPEN:
                iv_circle.setImageResource(R.drawable.ic_remove_circle_black_24dp);
                break;
            case LevelBean.STATE_NULL:
                iv_circle.setImageResource(R.drawable.ic_fill_circle_black_24dp);
                break;
        }
    }

    List<LevelBean> mDatas;

    private List<LevelBean> getDatas() {
        Random random = new Random();
        mDatas = new ArrayList<>();
        List<LevelBean> levelBeanList2 = new ArrayList<>();
        List<LevelBean> levelBeanList3 = new ArrayList<>();
        LevelBean bean1;
        LevelBean bean2;
        LevelBean bean3;
        for (int i = 0; i < 10; i++) {
            bean1 = new LevelBean("LEVEL_ITEM_1_INDEX_" + i, 10 + random.nextInt(20), R.color.colorGreen);
            bean1.setPosition(i);
            bean1.setState(LevelBean.STATE_CLOSE);
            bean1.setParent(null);
            for (int j = 0; j < 10; j++) {
                bean2 = new LevelBean("LEVEL_ITEM_2_INDEX_" + i + "_" + j, 10 + random.nextInt(20), R.color.colorBlue);
                bean2.setState(LevelBean.STATE_CLOSE);
                bean2.setParent(bean1);
                for (int k = 0; k < 10; k++) {
                    bean3 = new LevelBean("LEVEL_ITEM_3_INDEX_" + i + "_" + j + "_" + k, 10 + random.nextInt(20), R.color.colorOrange);
                    bean3.setState(LevelBean.STATE_NULL);
                    bean3.setParent(bean2);
                    levelBeanList3.add(bean3);
                }
                bean2.setChildren(levelBeanList3);
                levelBeanList3.clear();
                levelBeanList2.add(bean2);
            }
            bean1.setChildren(levelBeanList2);
            levelBeanList2.clear();
            mDatas.add(bean1);
        }
        return mDatas;
    }


    class MyAdapter implements BaseAdapter {


        @Override
        public View getView(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_table, null);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.textView);
            textView.setText("第-" + row + "-行-" + column + "-列");
            int viewType = getItemViewType(row, column);
            Log.e("MainActivity", "viewType = " + viewType);
            textView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, colors[viewType]));
            return convertView;
        }

        @Override
        public int getItemViewType(int row, int column) {
            if (row == 0 && column == 0) {//左上角的View
                return 0;
            } else if (column == 0) {//左边列表
                return 1;
            } else if (row == 0) {//上边列表
                return 2;
            } else {//内容部分
                return 3;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 4;
        }

        @Override
        public int getWidth(int column) {
            return 300;
        }

        @Override
        public int getHeight(int row) {
            return 100;
        }

        @Override
        public int getRowCount() {
            return 20;
        }

        @Override
        public int getColumnCount() {
            return 30;
        }
    }
}

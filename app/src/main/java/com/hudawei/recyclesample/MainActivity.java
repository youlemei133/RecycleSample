package com.hudawei.recyclesample;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int[] colors = {R.color.colorAccent, R.color.colorPrimary, R.color.colorGreen, R.color.colorBlue};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableView tableView = (TableView) findViewById(R.id.tableView);
        tableView.setAdapter(new MyAdapter());
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.e("MainActivity","widthPixels : "+metrics.widthPixels + "\theightPixels : " +metrics.heightPixels);
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

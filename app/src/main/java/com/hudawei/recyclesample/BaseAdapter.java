package com.hudawei.recyclesample;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hudawei on 2017/6/6.
 */

public interface BaseAdapter {

    View getView(int row, int column, View convertView, ViewGroup parent);

    int getItemViewType(int row, int column);

    int getViewTypeCount();

    int getWidth(int column);

    int getHeight(int row);

    int getRowCount();

    int getColumnCount();
}

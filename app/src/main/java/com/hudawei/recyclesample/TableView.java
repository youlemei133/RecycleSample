package com.hudawei.recyclesample;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hudawei on 2017/6/6.
 */

public class TableView extends ViewGroup {
    private BaseAdapter mAdapter;
    private int mRowCount;
    private int mColumnCount;
    private int[] widhts;
    private int[] heights;
    private Recycler mRecycler;
    private int mFirstRow = 1;
    private int mFirstColumn = 1;
    private View headView;
    private int mScrollX = 150;
    private int mScrollY = 50;

    public TableView(Context context) {
        this(context, null);
    }

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Handler handler = new Handler(Looper.myLooper());

    public void setAdapter(BaseAdapter adapter) {
        mAdapter = adapter;
        mRecycler = new Recycler(mAdapter.getViewTypeCount());
        requestLayout();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScrollY += 20;
                requestLayout();
                handler.postDelayed(this, 500);
            }
        }, 500);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = r - l;
        int height = b - t;
        width = Math.min(width, sumArray(widhts));
        height = Math.min(height, sumArray(heights));


        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        //内容部分
        top = heights[0] - mScrollY;
        for (int i = mFirstRow; i < mRowCount && top < height; i++) {
            bottom = top + heights[i];
            left = widhts[0] - mScrollX;
            for (int j = mFirstColumn; j < mColumnCount && left < width; j++) {
                right = left + widhts[j];
                makeAndStep(left, top, i, j, widhts[j], heights[i]);
                left = right;
            }
            top = bottom;
        }

        //上边部分
        left = widhts[0] - mScrollX;
        for (int i = mFirstColumn; i < mColumnCount && left < width; i++) {
            right = left + widhts[i];
            View view = makeAndStep(left, 0, 0, i, widhts[i], heights[0]);
            left = right;
        }

        //左边部分
        top = heights[0] - mScrollY;
        for (int i = mFirstRow; i < mRowCount && top < height; i++) {
            bottom = top + heights[i];
            View view = makeAndStep(0, top, i, 0, widhts[0], heights[i]);
            top = bottom;
        }

        headView = makeAndStep(0, 0, 0, 0, widhts[0], heights[0]);
    }

    private View makeAndStep(int left, int top, int row, int column, int width, int height) {
        View view = obtainView(row, column, width, height);

        if (view == null)
            throw new NullPointerException("第" + row + "行" + column + "列，Adapter.getView()方法返回null");

        view.layout(left, top, left + width, top + height);

        return view;
    }

    private View obtainView(int row, int column, int width, int height) {
        int viewType = mAdapter.getItemViewType(row, column);
        View scrapView = mRecycler.popRecycleView(viewType);
        View childView = mAdapter.getView(row, column, scrapView, this);
        childView.setTag(R.id.tab_item_column, column);
        childView.setTag(R.id.tab_item_row, row);
        childView.setTag(R.id.tab_item_type, viewType);

//        if (scrapView != childView) {
//            mRecycler.addRecycleView(viewType, childView);
//        }

        childView.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

        widhts[column] = Math.min(childView.getMeasuredWidth(), widhts[column]);
        heights[row] = Math.min(childView.getMeasuredHeight(), heights[row]);

        addTableView(childView, row, column);
        return childView;
    }

    private void addTableView(View childView, int row, int column) {
        if (row == 0 && column == 0) {
            addView(childView, getChildCount() - 1);
        } else if (row == 0 || column == 0) {
            addView(childView, getChildCount() - 2);
        } else {
            addView(childView);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (mAdapter != null) {
            mRowCount = mAdapter.getRowCount();
            mColumnCount = mAdapter.getColumnCount();

            widhts = new int[mColumnCount];
            heights = new int[mRowCount];

            for (int i = 0; i < mColumnCount; i++) {
                widhts[i] = mAdapter.getWidth(i);
            }
            for (int i = 0; i < mRowCount; i++) {
                heights[i] = mAdapter.getHeight(i);
            }
        }


        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = Math.min(widthSize, sumArray(widhts));
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = Math.min(heightSize, sumArray(heights));
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    private int sumArray(int[] arrays) {
        int result = 0;
        if (arrays != null && arrays.length > 0) {
            for (int value : arrays) {
                result += value;
            }
        }
        return result;
    }
}

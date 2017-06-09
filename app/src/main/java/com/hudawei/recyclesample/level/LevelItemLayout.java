package com.hudawei.recyclesample.level;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hudawei.recyclesample.R;

/**
 * Created by hudawei on 2017/6/9.
 */

public class LevelItemLayout extends RelativeLayout implements View.OnClickListener{
    private ImageView iv_circle;
    private TextView tv_name;
    private TextView tv_num;
    private int mState;

    public LevelItemLayout(Context context) {
        this(context, null);
    }

    public LevelItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_level_layout, this, true);

        iv_circle = (ImageView) findViewById(R.id.iv_circle);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_num = (TextView) findViewById(R.id.tv_num);

        iv_circle.setOnClickListener(this);
        mState = LevelBean.STATE_CLOSE;
    }

    public void bindName(String name) {
        tv_name.setText(name);
    }

    public void bindNum(int num) {
        tv_num.setText(num + "道");
    }

    public void bindState(int state) {
        if (mState == state)
            return;
        switch (state) {
            case LevelBean.STATE_CLOSE:
                iv_circle.setImageResource(R.drawable.ic_add_circle_black_24dp);
                break;
            case LevelBean.STATE_OPEN:
                iv_circle.setImageResource(R.drawable.ic_remove_circle_black_24dp);
                break;
            case LevelBean.STATE_NULL:
                iv_circle.setImageResource(R.drawable.ic_fill_circle_black_24dp);
                break;
            default:
                throw new IllegalArgumentException("state只能为LevelBean中的3种状态，当前状态为：" + state);
        }
        mState = state;
    }

    @Override
    public void onClick(View v) {
        switch (mState) {
            case LevelBean.STATE_CLOSE:
                bindState(LevelBean.STATE_OPEN);
                break;
            case LevelBean.STATE_OPEN:
                bindState(LevelBean.STATE_CLOSE);
                break;
        }
    }
}

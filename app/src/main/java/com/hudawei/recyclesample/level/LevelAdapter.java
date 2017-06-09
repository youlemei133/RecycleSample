package com.hudawei.recyclesample.level;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hudawei.recyclesample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hudawei on 2017/6/9.
 */

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHold> {
    Context mContext;
    List<LevelBean> mDatas;

    public LevelAdapter(Context context, List<LevelBean> levelBeans) {
        mContext = context;
        mDatas = levelBeans;
    }


    @Override
    public LevelViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_level_layout, parent, false);

        return new LevelViewHold(view);
    }

    @Override
    public void onBindViewHolder(LevelViewHold holder, int position) {
        LevelBean levelBean = mDatas.get(position);
        holder.tv_name.setText(levelBean.getName());
        holder.tv_num.setText(levelBean.getNum() + "道");
        holder.rv_main.setBackgroundColor(ContextCompat.getColor(mContext, levelBean.getColor()));

        holder.iv_circle.setTag(R.id.level_item_position, position);

        switch (levelBean.getState()) {
            case LevelBean.STATE_CLOSE:
                holder.iv_circle.setImageResource(R.drawable.ic_add_circle_black_24dp);
                break;
            case LevelBean.STATE_OPEN:
                holder.iv_circle.setImageResource(R.drawable.ic_remove_circle_black_24dp);
                break;
            case LevelBean.STATE_NULL:
                holder.iv_circle.setImageResource(R.drawable.ic_fill_circle_black_24dp);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    private void performClick(LevelBean bean, int position) {
        Log.e("LevelViewHold", "onClick   " + position + "\n " + bean);
        int state = bean.getState();
        List<LevelBean> children = bean.getChildren();
        //设置状态
        //更新视图
        switch (state) {
            case LevelBean.STATE_CLOSE:
                bean.setState(LevelBean.STATE_OPEN);
                if (children != null && children.size() != 0) {
                    notifyItemChanged(position);
                    mDatas.addAll(position + 1, children);
                    Log.e("LevelViewHold", "size:" + mDatas.size());
                    for (int i = position + 1; i < mDatas.size(); i++) {
                        mDatas.get(i).setPosition(i);
                    }
                    notifyItemRangeInserted(position + 1, children.size());
                    notifyItemRangeChanged(position + 1, mDatas.size());
                }
                break;
            case LevelBean.STATE_OPEN:
                bean.setState(LevelBean.STATE_CLOSE);
                if (children != null && children.size() != 0) {
                    notifyItemChanged(position);
                    List<LevelBean> allChildren = looperChildren(bean, new ArrayList<LevelBean>());
                    mDatas.removeAll(allChildren);
                    for (int i = position + 1; i < mDatas.size(); i++) {
                        mDatas.get(i).setPosition(i);
                    }
                    notifyItemRangeRemoved(position + 1, allChildren.size());
                    notifyItemRangeChanged(position + 1, mDatas.size());
                }
                break;
        }
        for (int i = 0; i < children.size(); i++) {
            children.get(i).setPosition(i + position + 1);
        }

    }

    private List<LevelBean> looperChildren(LevelBean bean, List<LevelBean> allChildren) {
        List<LevelBean> subBeanList = bean.getChildren();
        if (subBeanList != null && subBeanList.size() != 0) {
            bean.setState(LevelBean.STATE_CLOSE);
            allChildren.addAll(subBeanList);
            for (LevelBean b : subBeanList) {
                looperChildren(b, allChildren);
            }
        }
        return allChildren;
    }

    class LevelViewHold extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_circle;
        TextView tv_name;
        TextView tv_num;
        RelativeLayout rv_main;

        public LevelViewHold(View itemView) {
            super(itemView);
            iv_circle = (ImageView) itemView.findViewById(R.id.iv_circle);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            rv_main = (RelativeLayout) itemView.findViewById(R.id.rv_main);
            iv_circle.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (mDatas != null) {
                int position = (int) iv_circle.getTag(R.id.level_item_position);
                if (position >= 0 && position < mDatas.size()) {
                    LevelBean bean = mDatas.get(position);
                    if (bean != null)
                        performClick(bean, position);
                }
            }
        }

    }
}

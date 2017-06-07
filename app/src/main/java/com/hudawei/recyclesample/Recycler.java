package com.hudawei.recyclesample;

import android.view.View;

import java.util.Stack;

/**
 * Created by hudawei on 2017/6/6.
 *
 *
 */

public class Recycler {
    private Stack<View>[] views;
    public Recycler(int type){
        views = new Stack[type];
        for(int i=0;i<type;i++){
            views[i] = new Stack<View>();
        }
    }

    public void addRecycleView(int viewType,View view){
        views[viewType].push(view);
    }

    public View popRecycleView(int viewType){
        try {
            return views[viewType].pop();
        }catch (Exception e){

        }
        return null;
    }
}

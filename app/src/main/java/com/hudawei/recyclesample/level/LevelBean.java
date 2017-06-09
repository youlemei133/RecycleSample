package com.hudawei.recyclesample.level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hudawei on 2017/6/9.
 */

public class LevelBean {
    //该Item为关闭状态
    public final static int STATE_CLOSE = 0;
    //该Item为打开状态
    public final static int STATE_OPEN = 1;
    public final static int STATE_NULL = 2;

    //该Item的父容器Id
    private LevelBean parent;
    //该Item的子类id集合
    private List<LevelBean> children;
    //状态
    private int state;

    private String name;
    private int num;
    private int position;
    private int color;

    public LevelBean(String name, int num,int color) {
        this.name = name;
        this.num = num;
        this.color = color;
    }

    public LevelBean getParent() {
        return parent;
    }

    public void setParent(LevelBean parent) {
        this.parent = parent;
    }

    public List<LevelBean> getChildren() {
        return children;
    }

    public void setChildren(List<LevelBean> children) {
        if (children != null && children.size() != 0) {
            this.children = new ArrayList<>();
            for (LevelBean levelBean : children) {
                this.children.add(levelBean);
            }
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "LevelBean{" +
                "parent=" + parent +
                ", state=" + state +
                ", name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}

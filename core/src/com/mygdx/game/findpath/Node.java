package com.mygdx.game.findpath;

/**
 * @author xiangchijie
 * @date 2021/4/6 4:34 下午
 */
public class Node {

    private int index;
    private int x;
    private int y;

    public Node(int index, int x, int y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

package com.mygdx.game.head;

import com.badlogic.gdx.math.Circle;

/**
 * @author xiangchijie
 * @date 2021/3/21 9:50 下午
 */
public class HeadInfo {

    public Circle circle;

    public int vx;

    public int vy;

    public HeadInfo(Circle circle, int vx, int vy) {
        this.circle = circle;
        this.vx = vx;
        this.vy = vy;
    }
}

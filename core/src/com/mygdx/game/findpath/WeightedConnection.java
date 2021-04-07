package com.mygdx.game.findpath;

import com.badlogic.gdx.ai.pfa.Connection;

/**
 * 加权图连接
 *
 * @author xiangchijie
 * @date 2021/4/6 5:42 下午
 */
public class WeightedConnection<N> implements Connection<N> {

    protected N fromNode;
    protected N toNode;
    protected float cost;

    public WeightedConnection(N fromNode, N toNode, float cost) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.cost = cost;
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public N getFromNode() {
        return fromNode;
    }

    @Override
    public N getToNode() {
        return toNode;
    }

}

package com.mygdx.game.findpath;

import com.badlogic.gdx.ai.pfa.*;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.Array;

/**
 * 自动寻路
 *
 * @author xiangchijie
 * @date 2021/4/6 4:14 下午
 */
public class FindPath {

    public static void main(String[] args) {
        final Node n1 = new Node(1);
        final Node n2 = new Node(2);
        final Node n3 = new Node(3);
        final Node n4 = new Node(4);
        final Node n5 = new Node(5);

        // 创建连通图
        IndexedGraph<Node> graph = new IndexedGraph<Node>() {
            @Override
            public Array<Connection<Node>> getConnections(Node fromNode) {
                Array<Connection<Node>> connections = new Array<Connection<Node>>();
                switch (fromNode.getId()) {
                    case 1:
                        connections.add(new WeightedConnection<Node>(n1, n2,1));
                        break;
                    case 2:
                        connections.add(new WeightedConnection<Node>(n2, n3,1));
                        connections.add(new WeightedConnection<Node>(n2, n5,5));
                        break;
                    case 3:
                        connections.add(new WeightedConnection<Node>(n3, n4,1));
                        break;
                    case 4:
                        connections.add(new WeightedConnection<Node>(n4, n5,1));
                        break;
                    case 5:
                        connections.add(new WeightedConnection<Node>(n5, n4,1));
                        break;
                }
                return connections;
            }

            @Override
            public int getIndex(Node node) {
                return node.getId() - 1;
            }

            @Override
            public int getNodeCount() {
                return 5;
            }
        };

        IndexedAStarPathFinder<Node> pathFinder = new IndexedAStarPathFinder<Node>(graph);
        GraphPath<Connection<Node>> out = new DefaultGraphPath<Connection<Node>>();
        // Heuristic 启发函数
        pathFinder.searchConnectionPath(n1, n5, new Heuristic<Node>() {
            @Override
            public float estimate(Node node, Node endNode) {
                // 2到5的距离
                if(node.getId() * endNode.getId() == 10){
                    return 5;
                }
                return 1;
            }
        }, out);

        out.forEach(x -> {
            int i = x.getToNode().getId();
            System.out.println(i);
        });

    }

}

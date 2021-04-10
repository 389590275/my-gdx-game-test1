package com.mygdx.game.tiled;

import com.badlogic.gdx.ai.pfa.*;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.findpath.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiangchijie
 * @date 2021/4/7 6:39 下午
 */
public class TiledMapCache {

    public static final int w = 16;
    public static final int h = 10;
    public static IndexedGraph<Node> graph;
    public static Node[][] nodes = new Node[w][h];


    public static void initMap(TiledMap map) {
        //初始化节点
        int index = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++, index++) {
                nodes[x][y] = new Node(index, x, y);
            }
        }
        // 创建连通图
        graph = new IndexedGraph<Node>() {
            @Override
            public Array<Connection<Node>> getConnections(Node fromNode) {
                Array<Connection<Node>> connections = new Array<Connection<Node>>();
                int x = fromNode.getX();
                int y = fromNode.getY();
                if (!isRoad(map, x, y))
                    return connections;
                Node now = getNode(x, y);
                // 判断四个节点
                if (isRoad(map, x - 1, y)) {
                    connections.add(new DefaultConnection<>(now, getNode(x - 1, y)));
                }
                if (isRoad(map, x + 1, y)) {
                    connections.add(new DefaultConnection<>(now, getNode(x + 1, y)));
                }
                if (isRoad(map, x, y - 1)) {
                    connections.add(new DefaultConnection<>(now, getNode(x, y - 1)));
                }
                if (isRoad(map, x, y + 1)) {
                    connections.add(new DefaultConnection<>(now, getNode(x, y + 1)));
                }
                return connections;
            }

            @Override
            public int getIndex(Node node) {
                return node.getIndex();
            }

            @Override
            public int getNodeCount() {
                return h * w;
            }
        };
    }

    public static GraphPath<Connection<Node>> findPath(Node start, Node end) {
        IndexedAStarPathFinder<Node> pathFinder = new IndexedAStarPathFinder<Node>(graph);
        GraphPath<Connection<Node>> out = new DefaultGraphPath<Connection<Node>>();
        RandomXS128 randomXS128 = new RandomXS128();
        pathFinder.searchConnectionPath(start, end, new Heuristic<Node>() {
            // Heuristic 启发函数 h() 表示当前节点到终点的直线距离
            @Override
            public float estimate(Node node, Node endNode) {
                 return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY());
            }
        }, out);
        return out;
    }


    /**
     * 是否是路
     *
     * @param cell
     * @return
     */
    public static boolean isRoad(TiledMapTileLayer.Cell cell) {
        TiledMapTile tile = cell.getTile();
        return tile.getProperties().get("road", false, Boolean.class);
    }

    //遍历所有层
    public static boolean isRoad(TiledMap map, int x, int y) {
        if (x < 0 || y < 0)
            return false;
        if (x >= w || y >= h) {
            return false;
        }
        int layersCount = map.getLayers().getCount();
        for (int i = 0; i < layersCount; i++) {
            TiledMapTileLayer mapLayer1 = (TiledMapTileLayer) map.getLayers().get(0);
            TiledMapTileLayer.Cell cell = mapLayer1.getCell(x, y);
            if (!isRoad(cell)) {
                return false;
            }
        }
        return true;
    }

    public static Node getNode(int x, int y) {
        return nodes[x][y];
    }

}

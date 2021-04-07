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
    public static Map<Integer, Node> nodeMap = new HashMap<>();


    public static void initMap(TiledMap map) {
        // 创建连通图
        graph = new IndexedGraph<Node>() {
            @Override
            public Array<Connection<Node>> getConnections(Node fromNode) {
                Array<Connection<Node>> connections = new Array<Connection<Node>>();
                int x = fromNode.getId() / 100;
                int y = fromNode.getId() % 100;
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
                int x = node.getId() / 100;
                int y = node.getId() % 100;
                return x + y * w;
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
        // Heuristic 启发函数
        pathFinder.searchConnectionPath(start, end, new Heuristic<Node>() {
            @Override
            public float estimate(Node node, Node endNode) {
                return randomXS128.nextFloat();
            }
        }, out);
        out.forEach(x -> {
            int i = x.getToNode().getId();
            System.out.println("x:" + i / 100 + " y:" + i % 100);
        });
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

    public static int getKey(int x, int y) {
        return x * 100 + y;
    }

    public static Node getNode(int x, int y) {
        int key = getKey(x, y);
        Node v = nodeMap.get(key);
        if (v == null) {
            v = new Node(key);
            nodeMap. put(key, v);
        }
        return v;
    }


}

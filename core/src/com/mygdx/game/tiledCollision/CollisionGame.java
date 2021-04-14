package com.mygdx.game.tiledCollision;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * @author xiangchijie
 * @date 2021/4/14 4:45 下午
 */
public class CollisionGame extends Game {

    TiledMap map;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    AssetManager assetManager = new AssetManager();
    SpriteBatch batch;


    @Override
    public void create() {
        batch = new SpriteBatch();
        TmxMapLoader tmxMapLoader = new TmxMapLoader();
        map = tmxMapLoader.load("碰撞.tmx");
        //unitScale 1px是多少个单位 1/32F 表示每个单位32px  默认为1
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1f / 32);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 16);
        // 等待资源加载完毕
        assetManager.finishLoading();
        test();
    }

    @Override
    public void render() {
        ScreenUtils.clear(205, 205, 205, 0);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    private void test() {
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                isRoad(map, x, y);
            }
        }
    }

    //遍历所有层
    public static boolean isRoad(TiledMap map, int x, int y) {
        if (x < 0 || y < 0)
            return false;
        if (x >= 16 || y >= 16) {
            return false;
        }
        int layersCount = map.getLayers().getCount();
        for (int i = 0; i < layersCount; i++) {
            TiledMapTileLayer mapLayer1 = (TiledMapTileLayer) map.getLayers().get(i);
            TiledMapTileLayer.Cell cell = mapLayer1.getCell(x, y);
            if (cell == null) {
                continue;
            }
            if (!isRoad(cell)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isRoad(TiledMapTileLayer.Cell cell) {
        PolygonMapObject polygonMapObject = cell.getTile().getObjects().getByType(PolygonMapObject.class).get(0);
        Polygon polygon = polygonMapObject.getPolygon();
        if (Intersector.overlapConvexPolygons(polygon, new Polygon())) {
            // collision happened
            return false;
        }
        TiledMapTile tile = cell.getTile();
        return tile.getProperties().get("road", false, Boolean.class);
    }

}


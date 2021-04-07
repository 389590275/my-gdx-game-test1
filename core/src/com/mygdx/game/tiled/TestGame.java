package com.mygdx.game.tiled;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.findpath.Node;

/**
 * @author xiangchijie
 * @date 2021/4/5 9:43 下午
 */
public class TestGame extends Game {

    TiledMap map;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    GraphPath<Connection<Node>> path;
    AssetManager assetManager = new AssetManager();
    SpriteBatch batch;
    String file = "atlas/player0001.atlas";
    int curI = 0;


    @Override
    public void create() {
        batch = new SpriteBatch();
        TmxMapLoader tmxMapLoader = new TmxMapLoader();
        map = tmxMapLoader.load("blocks.tmx");
        //unitScale 1px是多少个单位 1/32F 表示每个单位32px  默认为1
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1f / 32);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 10);
        //初始化地图
        TiledMapCache.initMap(map);

        Node start = TiledMapCache.getNode(0, 0);
        Node end = TiledMapCache.getNode(14, 0);
        this.path = TiledMapCache.findPath(start, end);
        assetManager.load(file, TextureAtlas.class);
        // 等待资源加载完毕
        assetManager.finishLoading();
    }

    float time;

    @Override
    public void render() {
        ScreenUtils.clear(205, 205, 205, 0);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        TextureAtlas textureAtlas = assetManager.get(file, TextureAtlas.class);
        TextureRegion textureRegion = textureAtlas.findRegion("01");

        batch.begin();
        for (int i = 0; i <= curI && i < path.getCount(); i++) {
            Node node = path.get(i).getToNode();
            int id = node.getId();
            int x = id / 100;
            int y = id % 100;
            batch.draw(textureRegion, x * 32, y * 32, 30, 30);
        }
        batch.end();
        time += Gdx.graphics.getDeltaTime();
        if(time>0.3){
            time = 0;
            curI++;
        }
    }

}


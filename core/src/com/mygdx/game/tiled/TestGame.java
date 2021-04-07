package com.mygdx.game.tiled;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * @author xiangchijie
 * @date 2021/4/5 9:43 下午
 */
public class TestGame extends Game {

    TiledMap map;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;

    @Override
    public void create() {
        TmxMapLoader tmxMapLoader = new TmxMapLoader();
        map=tmxMapLoader.load("blocks.tmx");
        //unitScale 1px是多少个单位 1/32F 表示每个单位32px  默认为1
        tiledMapRenderer= new OrthogonalTiledMapRenderer(map,1f/32);
        camera=new OrthographicCamera();
        camera.setToOrtho(false, 16, 10);
    }

    @Override
    public void render() {
        //Gdx.gl.glClearColor(205,205,205,0);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(205,205,205,0);

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

}


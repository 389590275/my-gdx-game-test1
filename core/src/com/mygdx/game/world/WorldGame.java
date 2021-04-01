package com.mygdx.game.world;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class WorldGame extends Game {

    GameStage gameStage = null;

    @Override
    public void create() {
        gameStage = new GameStage();
        Gdx.input.setInputProcessor(gameStage);
    }

    @Override
    public void render() {
        gameStage.draw();
    }

    @Override
    public void dispose() {
        gameStage.dispose();
        super.dispose();
    }
}
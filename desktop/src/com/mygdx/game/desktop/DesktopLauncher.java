package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.balloon.BalloonGame;
import com.mygdx.game.tiled.TestGame;
import com.mygdx.game.tiledCollision.CollisionGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 300;
        config.width = 512;
        config.resizable = false;
        String tag = "0-0";
        new LwjglApplication(new TestGame(), config);
    }
}

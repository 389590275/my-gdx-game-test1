package com.mygdx.game.balloon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

/**
 * @author xiangchijie
 * @date 2021/3/18 2:27 下午
 */
public class BalloonGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture texture;
    Pixmap pixmap;
    private int[] v = {1, 1};

    private static final int bigR = 50;
    private static final int smallR = 10;
    private Circle bigBalloon;
    private Circle smallBalloon;


    @Override
    public void create() {
        batch = new SpriteBatch();
        bigBalloon = new Circle(500 - bigR, 500 - bigR, bigR);
        smallBalloon = new Circle(smallR, smallR, smallR);
        pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        // pixmap 处理完成后转换成纹理
        texture = new Texture(pixmap);
    }

    @Override
    public void render() {
        updateBalloon();
        // 黑色清屏
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // 在屏幕左下角绘制纹理
        batch.draw(texture, 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        // 当应用退出时释放资源
        if (batch != null) {
            batch.dispose();
        }
        if (texture != null) {
            texture.dispose();
        }
        pixmap.dispose();
    }

    private void updateBalloon() {
        if(bigBalloon.overlaps(smallBalloon)){
            return;
        }
        updatePosition();

        // 设置绘图颜色为白色
        pixmap.setColor(1, 1, 1, 1);
        // 将整个 pixmap 填充为当前设置的颜色
        pixmap.fill();
        // 画大圆
        // 设置绘图颜色为红色
        pixmap.setColor(1, 0, 0, 1);
        // 画一个空心圆
        pixmap.drawCircle((int) bigBalloon.x, (int) bigBalloon.y, (int) bigBalloon.radius);


        //画小圆
        // 设置绘图颜色为红色
        pixmap.setColor(1, 0, 0, 1);
        // 画一个空心圆
        pixmap.drawCircle((int) smallBalloon.x, (int) smallBalloon.y, (int) smallBalloon.radius);
        texture.dispose();
        // pixmap 处理完成后转换成纹理
        texture = new Texture(pixmap);
    }

    private void updatePosition() {
        bigBalloon.setX(bigBalloon.x + v[0]);
        bigBalloon.setY(bigBalloon.y + v[1]);
        if (bigBalloon.x > Gdx.graphics.getWidth() - bigR) {
            v[0] = -1;
        } else if (bigBalloon.x < bigBalloon.radius) {
            v[0] = 1;
        }
        if (bigBalloon.y > Gdx.graphics.getHeight() - bigR) {
            v[1] = -1;
        } else if (bigBalloon.y < bigBalloon.radius) {
            v[1] = 1;
        }
    }


}

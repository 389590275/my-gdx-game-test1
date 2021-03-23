package com.mygdx.game.head;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

/**
 * @author xiangchijie
 * @date 2021/3/18 2:27 下午
 */
public class BigHeadGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture bigTexture;
    Texture smallTexture;

    private static final int r = 50;
    private HeadInfo bigHead = new HeadInfo(new Circle(r, r, r), 20, 4);
    private HeadInfo smallHead = new HeadInfo(new Circle(300, 300, r), -10, 5);

    @Override
    public void create() {
        batch = new SpriteBatch();
        bigTexture = new Texture(Gdx.files.internal("siyu.png"));
        smallTexture = new Texture(Gdx.files.internal("daxiang.png"));
    }

    @Override
    public void render() {
        updateBalloon();
        // 黑色清屏
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // 在屏幕左下角绘制纹理
        batch.draw(bigTexture, bigHead.circle.x, bigHead.circle.y);
        batch.draw(smallTexture, smallHead.circle.x, smallHead.circle.y);
        batch.end();
    }

    @Override
    public void dispose() {
        // 当应用退出时释放资源
        batch.dispose();
        smallTexture.dispose();
        bigTexture.dispose();
    }

    private void updateBalloon() {
        if (bigHead.circle.overlaps(smallHead.circle)) {
            int tmp = bigHead.vx;
            bigHead.vx = smallHead.vx;
            smallHead.vx = tmp;

            tmp = bigHead.vy;
            bigHead.vy = smallHead.vy;
            smallHead.vy = tmp;
        }
        updatePosition();
    }

    private void updatePosition() {
        updateHead(bigHead);
        updateHead(smallHead);
    }

    private void updateHead(HeadInfo headInfo) {
        headInfo.circle.setX(headInfo.circle.x + headInfo.vx);
        headInfo.circle.setY(headInfo.circle.y + headInfo.vy);
        if (headInfo.circle.x > Gdx.graphics.getWidth() - 2 * r) {
            headInfo.vx = -Math.abs(headInfo.vx);
        } else if (headInfo.circle.x < 0) {
            headInfo.vx = Math.abs(headInfo.vx);
        }

        if (headInfo.circle.y > Gdx.graphics.getHeight() - 2 * r) {
            headInfo.vy = -Math.abs(headInfo.vy);
        } else if (headInfo.circle.y < 0) {
            headInfo.vy = Math.abs(headInfo.vy);
        }
    }

}

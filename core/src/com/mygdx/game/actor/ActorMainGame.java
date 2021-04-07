package com.mygdx.game.actor;

/**
 * @author xiangchijie
 * @date 2021/4/2 2:44 下午
 */

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * 游戏主程序的启动入口类
 */
public class ActorMainGame extends ApplicationAdapter {

    // 纹理
    private Texture texture;

    // 舞台
    private Stage stage;


    // 自定义的演员
    private MyActor actor;


    @Override
    public void create() {
        // 创建纹理, badlogic.jpg 图片的宽高为 256 * 256
        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        // 创建演员
        actor = new MyActor(new TextureRegion(texture));
        // 创建舞台, 并添加演员
        stage = new Stage();
        stage.addActor(actor);

        /* 设置演员属性的值 */

        // 设置演员的坐标
        actor.setPosition(50, 100);        // 或者 setX(), setY() 分开设置

        // 设置演员 旋转和缩放支点 为演员的左下角
        actor.setOrigin(0, 0);

        // 设置演员缩放比, X 轴方向缩小到 0.5 倍, Y 轴方向保持不变
        actor.setScale(0.5F, 1.0F);


        /* 事件初始化 */

        // 首先必须注册输入处理器（stage）, 将输入的处理设置给 舞台（Stage 实现了 InputProcessor 接口）
        // 这样舞台才能接收到输入事件, 分发给相应的演员 或 自己处理。
        Gdx.input.setInputProcessor(stage);
        // 顺时针旋转 5 度
        actor.setRotation(-5);
        actor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                actor.setRotation(actor.getRotation() - 1);
            }
        });
    }

    @Override
    public void render() {
        // 黑色清屏
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        // 绘制舞台，并批处理舞台中的演员（自动逐个调用演员的 draw() 方法绘制演员）
        stage.draw();
    }

    @Override
    public void dispose() {
        // 释放资源
        if (texture != null) {
            texture.dispose();
        }
        if (stage != null) {
            stage.dispose();
        }
    }

}

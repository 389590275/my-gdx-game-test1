package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameStage extends Stage {

    private World world;
    private Box2DDebugRenderer renderer; // 测试用绘制器


    public GameStage() {

        renderer = new Box2DDebugRenderer();
        //设置world参数，9.8是重力加速度，true代表不去模拟已经停止失效的物体
        world = new World(new Vector2(0, -9.8f), true); // 标准重力场
        CircleShape circleShape = new CircleShape();// 创建一个圆
        circleShape.setRadius(3f); // 设置半径
        addShape(circleShape, new Vector2(16, 70), BodyDef.BodyType.DynamicBody);

        EdgeShape edgeShape = new EdgeShape();// 创建一个边
        edgeShape.set(new Vector2(5, 35), new Vector2(30, 20));// 设置2点
        addShape(edgeShape, new Vector2(1, 0), BodyDef.BodyType.StaticBody);
    }

    @Override
    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);
        renderer.render(world, getCamera().combined);
        super.draw();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        world.dispose();
        super.dispose();
    }

    private void addShape(Shape shape, Vector2 pos, BodyDef.BodyType type) {
        BodyDef bd = new BodyDef();
        bd.position.set(pos.x, pos.y);
        bd.type = type;
        Body b = world.createBody(bd);
        b.createFixture(shape, 1f);
    }
}

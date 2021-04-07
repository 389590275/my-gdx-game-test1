package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameStage extends Stage {

    private World world;
    private Box2DDebugRenderer renderer; // 测试用绘制器
    Body circleBody;
    List<Body> balloonList = new CopyOnWriteArrayList<>();

    //设置world参数，9.8是重力加速度，true代表不去模拟已经停止失效的物体
    public static final float g = 9.8F;

    //G = Mg 重力 = 质量 * 重力加速度


    public GameStage() {
        renderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, -g), true); // 标准重力场

        CircleShape circleShape = new CircleShape();// 创建一个圆
        circleShape.setRadius(100f); // 设置半径

        circleBody = addShape(circleShape, new Vector2(250, 50), BodyDef.BodyType.DynamicBody);
        circleBody.setLinearVelocity(0, 30);
        circleBody.setBullet(true);
        circleBody.getFixtureList().get(0).setSensor(true);
        Random random = new Random();
        Body minCircleBody;
        for (int i = 0; i < 500; i += 5) {
            for (int j = 100; j < 500; j += 5) {
                if(random.nextInt(100) > 97){
                    CircleShape minCircleShape = new CircleShape();// 创建一个圆
                    minCircleShape.setRadius(2f); // 设置半径
                    minCircleBody = addShape(minCircleShape, new Vector2(i, j), BodyDef.BodyType.StaticBody);
                }
            }
        }


        world.setContactListener(new ContactListener(){
            @Override
            public void beginContact(Contact contact) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                Body small = a;
                if(a.equals(circleBody) ){
                    small = b;
                }
                world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);
                world.destroyBody(small);
                small.setActive(false);
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

    }

    @Override
    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);
        renderer.render(world, getCamera().combined);
        super.draw();
        float G = circleBody.getMass() * g;
        circleBody.applyForce(0, G, circleBody.getWorldCenter().x, circleBody.getWorldCenter().y, true);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        world.dispose();
        super.dispose();
    }

    private Body addShape(Shape shape, Vector2 pos, BodyDef.BodyType type) {
        BodyDef bd = new BodyDef();
        bd.position.set(pos.x, pos.y);
        bd.type = type;
        Body b = world.createBody(bd);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.5f;

        b.createFixture(fixtureDef);
        return b;
    }
}

package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	// 纹理画布
	SpriteBatch batch;
	// 纹理
	Texture img;
	Sprite sprite;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		/*
		 * 使用 assets 文件夹中的图片 badlogic.jpg 创建纹理,
		 * 文件路径相对于 assets 文件夹根目录, 如果图片放在子目录, 则路径为 "xxx/badlogic.jpg"
		 */
		img = new Texture("badlogic.jpg");
		sprite = new Sprite(img);
		// 设置精灵的位置（左下角绘制起点）
		sprite.setPosition(64, 128);
		sprite.setScale(0.5F, 0.5F);
	}

	@Override
	public void render () {
		// 设置清屏颜色为红色（RGBA）
		Gdx.gl.glClearColor(0, 0, 0, 1);
		// 清屏
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		sprite.draw(batch);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

}

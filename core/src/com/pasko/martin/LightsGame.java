package com.pasko.martin;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pasko.martin.game.Assets;
import com.pasko.martin.game.InputHandler;
import com.pasko.martin.game.WorldRenderer;
import com.pasko.martin.game.WorldController;


public class LightsGame extends Game {// ApplicationAdapter {
	private static final String TAG = LightsGame.class.getName();
	private WorldRenderer worldRenderer;
	private WorldController worldController;
	private InputHandler inputHandler;
	private boolean paused;


	
	@Override
	public void create () {
		AssetManager manager = new AssetManager();
		Assets.instance.init(manager);
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
		OrthographicCamera camera = worldRenderer.getCamera();
		inputHandler = new InputHandler(camera);
		inputHandler.addObserver(worldController.getPlayfield());
		paused = false;

	//	img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		if (paused) {
			return;
		}
		worldController.update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		worldRenderer.render();
	}

	@Override
	public void pause() {
		super.pause();
		paused = true;
	}

	@Override
	public void resume() {
		super.resume();
		paused = false;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		worldRenderer.resize(width, height);
	}

	@Override
	public void dispose () {
		worldRenderer.dispose();
		worldController.dispose();
	}
}

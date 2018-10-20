package com.pasko.martin.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pasko.martin.LightsGame;
import com.pasko.martin.game.Assets;
import com.pasko.martin.game.InputHandler;
import com.pasko.martin.game.WorldController;
import com.pasko.martin.game.WorldRenderer;

public abstract class AbstractScreen implements Screen {
    private static final String TAG = LightsGame.class.getName();
    protected Game game;
    protected boolean paused;

    public AbstractScreen(Game game) {
        paused = false;
        this.game = game;
    }

    public void render () {
        if (paused) {
            return;
        }
        clearScreen();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    abstract public void resize(int width, int height);

    @Override
    abstract public void dispose ();

}

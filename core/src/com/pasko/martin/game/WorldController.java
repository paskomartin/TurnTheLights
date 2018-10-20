package com.pasko.martin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.pasko.martin.game.objects.factories.GameBlockFactory;
import com.pasko.martin.utils.CameraHelper;
import com.pasko.martin.utils.Constants;

public class WorldController extends InputAdapter{
    private static final String TAG = WorldController.class.getName();

    public CameraHelper cameraHelper;
    public Array<Sprite> sprites;
    Texture texSpr;
    Playfield playfield;


    public WorldController() {
        init();
    }

    private void init() {
        //Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        createGameObjects();
        createSprites();
    }

    private void createGameObjects() {
        playfield = GameBlockFactory.instance.createGameObjects();
    }

    private void createSprites() {
        final int row = 5;
        final int col = 5;
        sprites = new Array<Sprite>();
        Sprite spr = null;
        texSpr = new Texture(Gdx.files.internal("badlogic.jpg"));
        float sprWidth = Constants.VIEWPORT_WIDTH / col;
        float sprHeight = Constants.VIEWPORT_HEIGHT / row;

        for (int y = 0; y < row; ++y) {
            for(int x = 0; x < col; ++x) {
                //spr = sprite = new Sprite(new Texture(Gdx.files.internal("badlogic.jpg")));
                spr = new Sprite(texSpr);
                spr.setFlip(false, true);
                spr.setSize(sprWidth, sprHeight);
                spr.setPosition(x * sprWidth, y * sprHeight);
                sprites.add(spr);
            }
        }
    }

    public void update(float deltaTime) {
        playfield.update(deltaTime);
    }

    public void dispose() {
       texSpr.dispose();
    }

    public Playfield getPlayfield() {
        return playfield;
    }
}

package com.pasko.martin.game.objects.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.pasko.martin.game.Assets;
import com.pasko.martin.game.Playfield;
import com.pasko.martin.game.objects.AbstractGameObject;
import com.pasko.martin.game.objects.GameBlock;
import com.pasko.martin.utils.Constants;

public class GameBlockFactory {
    public static final String TAG = GameBlockFactory.class.getName();
    public static final GameBlockFactory instance = new GameBlockFactory();

    private GameBlockFactory() {}

    /* TODO: In the future i'll put here reading level from assets manager
     */
    // TODO: TEMPORARY
    public Playfield createGameObjects() {
        Playfield playfield = null;
        playfield = createPlayfield();
        createLightArea();
        return playfield;
    }

    private Playfield createPlayfield() {
        final int rows = 8;
        final int cols = 8;
        Array<AbstractGameObject> gameObjects = new Array<AbstractGameObject>();

        /*
        Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        TextureRegion region = new TextureRegion(texture, texture.getWidth(), texture.getHeight());
        region.flip(false, true);
        */

        TextureRegion region = Assets.instance.block.turnOn;

        AbstractGameObject object = null;

        float sprWidth = Constants.VIEWPORT_WIDTH / cols;
        //float sprHeight = Constants.VIEWPORT_HEIGHT / rows;
        float sprHeight = sprWidth;

        // Portrait mode:   w = 1080
        //                  h = 2060
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();




        //          if rows > cols
 //       float aspect = sprHeight / sprWidth;
 //       sprWidth *= aspect;


        for (int y = 0; y < rows; ++y ) {
            for (int x = 0; x < cols; ++x) {
                Vector2 position = new Vector2(x * sprWidth, y * sprHeight);
                Vector2 size = new Vector2(sprWidth, sprHeight);
                object = new GameBlock(position,size, region, false);
                gameObjects.add(object);
            }
        }

        Playfield playfield = new Playfield(rows, cols, gameObjects);
        return  playfield;

/*
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
*/

    }


    private void createLightArea() {
    }

}

package com.pasko.martin.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pasko.martin.utils.Constants;


public class WorldRenderer implements Disposable {
    private static final String TAG = WorldRenderer.class.getName();
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private WorldController worldController;
    private BitmapFont font;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        final float aspectRatio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
        //viewport = new ExtendViewport(Constants.VIEWPORT_WIDTH * aspectRatio, Constants.VIEWPORT_HEIGHT, camera);

        Application.ApplicationType appType = Gdx.app.getType();
        if (appType == Application.ApplicationType.Android) {
            // android -- orientation portrait
            //viewport = new StretchViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT * aspectRatio, camera);

            viewport = new ExtendViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, camera);

            //viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        else {
            viewport = new ExtendViewport(Constants.VIEWPORT_WIDTH * aspectRatio, Constants.VIEWPORT_HEIGHT, camera);
        }

        //viewport = new ScreenViewport(camera);
        viewport.apply();

        camera.position.set(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_HEIGHT / 2, 0);
        camera.setToOrtho(true);
        camera.update();

        font = new BitmapFont(true);
        font.setColor(Color.RED);
        font.getData().setScale(0.5f, 0.5f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        initTestAnimation();
    }

    boolean animate;
    Animation<TextureRegion> anim;
    TextureRegion currentFrame = null;
    float elapsedTime = 0f;
    private void initTestAnimation() {
        animate = true;
        Array<TextureAtlas.AtlasRegion> regions = Assets.instance.block.blockAnim;
        anim = new Animation(1.0f / 30.f, regions, Animation.PlayMode.LOOP_PINGPONG);

    }


    public void render() {
        camera.update();
        elapsedTime += Gdx.graphics.getDeltaTime();
        currentFrame = anim.getKeyFrame(elapsedTime);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.playfield.render(batch);
        //batch.draw(currentFrame,0,0);
        batch.end();
    }

    private void renderTestSprites() {
        int col = 0;
        int row = 0;
        int iter = 0;
        for(Sprite sprite : worldController.sprites) {
            sprite.draw(batch);
            String str = "(" + Integer.toString(row) + ", " +  Integer.toString(col) + ")";
            font.setColor(Color.BLACK);
            font.draw(batch,str, sprite.getX() + 1, sprite.getY() + sprite.getHeight() / 4 + 1);
            font.setColor(Color.GREEN);
            font.draw(batch,str, sprite.getX(), sprite.getY() + sprite.getHeight() / 4);

            ++iter;
            row = iter / 5;
            col = iter % 5;
        }
        /*
        //camera.translate(-camera.viewportWidth / 2, camera.viewportHeight/ 2);
        //batch.draw(worldController.img,-3 * w, 2 * h, w, h );
        int startXPos = -2;
        int startYPos = -2;


        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                batch.draw(worldController.img, x * w + startXPos, y * h + startYPos, w, h);
            }
        }

        //batch.draw(worldController.img, 0, 0, w, h );
        */
    }

    public void resize(int width, int height) {
        /*
        // aspect ratio
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();
        */

        /*
        float w = width;
        float h = height;

        float aspectRatio = w / h;
        camera.viewportWidth = aspectRatio * Constants.VIEWPORT_WIDTH;
        camera.viewportHeight = Constants.VIEWPORT_HEIGHT;
        camera.update();

        */
        viewport.update(width, height);
        camera.position.set(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_HEIGHT / 2, 0);

    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}

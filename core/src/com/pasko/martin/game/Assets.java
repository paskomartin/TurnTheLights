package com.pasko.martin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.pasko.martin.utils.Constants;


public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    public AssetManager assetManager; // private

    private Assets() {}

    public AssetBlock block;


    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        loadAssets(assetManager);
        setAtlasTextureFilter(assetManager);
        createAssets();
    }

    private void loadAssets(AssetManager assetManager) {
        assetManager.load(Constants.BLOCK_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
    }

    private void setAtlasTextureFilter(AssetManager assetManager) {
        TextureAtlas atlas = assetManager.get(Constants.BLOCK_ATLAS);
        for (Texture texture : atlas.getTextures()) {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    private void createAssets() {
        TextureAtlas atlas = assetManager.get(Constants.BLOCK_ATLAS);
        block = new AssetBlock(atlas);

    }


    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
    }

    public void error(String filename, Class type, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + filename + "'", (Exception)throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }


    public class AssetBlock {
        public final TextureAtlas.AtlasRegion turnOff;
        public final TextureAtlas.AtlasRegion turnOn;
        public final Array<TextureAtlas.AtlasRegion> blockAnim;
        public final Array<TextureAtlas.AtlasRegion> blockDisappear;

        public AssetBlock(TextureAtlas atlas) {

            turnOff = atlas.findRegion("blockTurnOff");
            turnOff.flip(false, true);
            turnOn = atlas.findRegion("blockTurnOn");
            turnOn.flip(false, true);

            blockAnim = atlas.findRegions("blockAnim");
            flipAtlasRegions(blockAnim);
            blockDisappear = atlas.findRegions("blockDisappear");
            flipAtlasRegions(blockDisappear);
        }

        private void flipAtlasRegions(Array<TextureAtlas.AtlasRegion> regions) {
            for (TextureAtlas.AtlasRegion region : regions) {
                region.flip(false, true);
            }
        }

    }

}

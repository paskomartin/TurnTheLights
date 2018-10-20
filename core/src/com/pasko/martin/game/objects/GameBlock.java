package com.pasko.martin.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.pasko.martin.game.Assets;

public class GameBlock extends AbstractGameObject {
    private boolean lightOn;
    private GameBlockSprites sprites;
    private boolean disappear;
    private boolean animate;
    private float stateTime;

    private class GameBlockSprites {
        public TextureRegion texLightOn;
        public TextureRegion texLightOff;
        public Animation<TextureRegion> animTurnOn;
        public Animation<TextureRegion> animTurnOff;
        public Animation<TextureRegion> animDisappear;
        public TextureRegion currentTexture;
    }


    public GameBlock(Vector2 position, Vector2 size, TextureRegion texture, boolean lightOn) {
        super(position, size, null); //texture);
        this.lightOn = lightOn;
        init();
    }

    public void init() {
        animate = false;
        disappear = false;
        sprites = new GameBlockSprites();
        stateTime = 0;
        initSprites();
    }

    public void initSprites() {
        initTextures();
        initAnimations();
    }

    private void initTextures() {
        sprites.texLightOn = Assets.instance.block.turnOn;
        sprites.texLightOff = Assets.instance.block.turnOff;
        setCurrentTexture();
    }


    private void initAnimations() {
        Array<TextureAtlas.AtlasRegion> regions = Assets.instance.block.blockAnim;
        sprites.animTurnOn = new Animation(1.0f / 60.f, regions, Animation.PlayMode.NORMAL);
        sprites.animTurnOff = new Animation(1.0f / 60.f, regions, Animation.PlayMode.REVERSED);
        regions = Assets.instance.block.blockDisappear;
        sprites.animDisappear = new Animation(1.0f / 60.f, regions, Animation.PlayMode.NORMAL);
    }

    public boolean isLightOn() {
        return lightOn;
    }

    public void setLightOn(boolean lightOn) {
        this.lightOn = lightOn;
        //setCurrentTexture();
        setAnimation();
    }

    private void setAnimation() {
        animate = true;
        stateTime = 0;
    }


    private void setCurrentTexture() {
        if (lightOn) {
            sprites.currentTexture = sprites.texLightOn;
        } else {
            sprites.currentTexture = sprites.texLightOff;
        }
    }


    @Override
    public void update(float deltaTime) {
        if (animate) {
            stateTime += deltaTime;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        animate();


        //batch.draw(sprites.texLightOff, position.x, position.y, size.x, size.y);
        batch.draw(sprites.currentTexture, position.x, position.y, size.x, size.y);

        //batch.draw(tex, position.x, position.y, size.x, size.y);
        //batch.draw(worldController.img, x * w + startXPos, y * h + startYPos, w, h);
    }

    private void animate() {
        if (animate) {
            Animation<TextureRegion> animation;
            if (lightOn) {
                animation = sprites.animTurnOn;
            } else {
                animation = sprites.animTurnOff;
            }

            sprites.currentTexture = animation.getKeyFrame(stateTime);
            if (animation.isAnimationFinished(stateTime)) {
                animate = false;
                setCurrentTexture();
            }
        }
    }

}

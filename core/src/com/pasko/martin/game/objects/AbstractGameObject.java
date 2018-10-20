package com.pasko.martin.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

abstract public class AbstractGameObject {
    protected Vector2 position;
    protected Vector2 size;
    protected Rectangle collisionBox;
    protected TextureRegion tex;


    AbstractGameObject(Vector2 position, Vector2 size, TextureRegion texture) {
        this.position = position;
        this.size = size;
        this.tex = texture;
        collisionBox = new Rectangle(position.x, position.y, size.x, size.y);
    }

    public abstract void update(float deltaTime);
    public abstract void render(SpriteBatch batch);

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        collisionBox.setPosition(position.x, position.y);
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
        collisionBox.setSize(size.x, size.y);
    }

    public TextureRegion getTex() {
        return tex;
    }

    public void setTex(TextureRegion tex) {
        this.tex = tex;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }
}

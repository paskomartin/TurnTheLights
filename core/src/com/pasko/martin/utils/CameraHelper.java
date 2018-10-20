package com.pasko.martin.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

// https://github.com/saltares/libgdx-cookbook/blob/master/samples/core/src/com/cookbook/samples/OrthographicCameraSample.java
// 193
// https://github.com/libgdx/libgdx/wiki/Orthographic-camera
public class CameraHelper {
    private static final String TAG = CameraHelper.class.getName();
    private final float MAX_ZOOM_IN = 1f;
    private final float MAX_ZOOM_OUT = 5.0f;

    private Vector2 position;
    private float zoom;
    private Sprite target;

    //private GameObject target;

    public CameraHelper() {
        position = new Vector2();
        zoom = 1f;
    }

    public void update(float deltaTime) {
        if (!hasTarget()) {
            return;
        }

        position.x = target.getX() + target.getOriginX();
        position.y = target.getY() + target.getOriginY();

    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public void setPosition(Vector2 pos) {
        position = pos;
    }

    public void addZoom(float amount) {
        setZoom(zoom + amount);
    }

    public void setZoom(float zoom) {
        zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
    }

    public float getZoom() {
        return zoom;
    }

    public Sprite getTarget() {
        return target;
    }

    public void setTarget(Sprite target) {
        this.target = target;
    }

    public boolean hasTarget() {
        return target != null;
    }

    public boolean hasTarget(Sprite target) {
        return hasTarget() && this.target.equals(target);
    }

    public void applyTo(OrthographicCamera camera) {
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.zoom = zoom;
        camera.update();
    }
}

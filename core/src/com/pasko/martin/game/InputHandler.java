package com.pasko.martin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.pasko.martin.utils.InputTouchObserver;
import com.pasko.martin.utils.InputTouchSubject;

public class InputHandler extends InputAdapter {
    private OrthographicCamera camera;
    Vector3 worldTouchPosition;
    Vector3 screenTouchPosition;
    InputSubject inputSubject;

    class InputSubject extends InputTouchSubject {
        public InputSubject() {
            super();
        }

        @Override
        public void notifyObservers() {
            for(InputTouchObserver observer : observers) {
                observer.updateTouch(worldTouchPosition);
            }
        }
    }

    public InputHandler(OrthographicCamera camera) {
        this.camera = camera;
        worldTouchPosition = new Vector3();
        screenTouchPosition = new Vector3();
        inputSubject = new InputSubject();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenTouchPosition.set(screenX, screenY, 0);
        worldTouchPosition = camera.unproject(screenTouchPosition);
        inputSubject.notifyObservers();
        return super.touchDown(screenX, screenY, pointer, button);
    }

    public Vector3 getWorldTouchPosition() {
        return worldTouchPosition;
    }

    public void addObserver(InputTouchObserver observer) {
        inputSubject.registerObserver(observer);
    }

}

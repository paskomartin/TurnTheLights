package com.pasko.martin.utils;

import com.badlogic.gdx.utils.Array;
import com.pasko.martin.game.InputHandler;

public abstract class InputTouchSubject {
    protected Array<InputTouchObserver> observers;

    public InputTouchSubject() {
        observers = new Array<InputTouchObserver>();
    }

    public void registerObserver(InputTouchObserver observer) {
        observers.add(observer);
    }

    public boolean unregisterObserver(InputTouchObserver observer) {
        // remove this same object. using ==
        boolean result = false;
        result = observers.removeValue(observer, true);
        return  result;
    }

    abstract public void notifyObservers();
}

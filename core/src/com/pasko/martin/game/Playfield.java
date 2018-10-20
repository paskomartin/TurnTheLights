package com.pasko.martin.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.pasko.martin.game.objects.AbstractGameObject;
import com.pasko.martin.game.objects.GameBlock;
import com.pasko.martin.utils.InputTouchObserver;

public class Playfield implements InputTouchObserver{
    private int rows;
    private int cols;
    Array<AbstractGameObject> gameObjects;

    SelectionType selectionType;

    enum SelectionType {
        POINT,
        CROSS
    }

    public Playfield(int rows, int cols, Array<AbstractGameObject> gameObjects) {
        this.rows = rows;
        this.cols = cols;
        this.gameObjects = gameObjects;
        selectionType = SelectionType.CROSS;
    }

    public void update(float deltaTime) {
        for(AbstractGameObject gameObject : gameObjects) {
            gameObject.update(deltaTime);
        }
    }

    public void render(SpriteBatch batch) {
        for (AbstractGameObject gameObject : gameObjects) {
            gameObject.render(batch);
        }

    }

    public void setGameObjects(Array<AbstractGameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public void clearPlayfield() {
        gameObjects.clear();
    }

    @Override
    public void updateTouch(Vector3 worldTouchPosition) {
        checkCollisionWithBlocks(worldTouchPosition);
    }

    private void checkCollisionWithBlocks(Vector3 worldTouchPosition) {
        Rectangle worldClickRectangle = new Rectangle(worldTouchPosition.x, worldTouchPosition.y, 1, 1);

        for (int index = 0; index < gameObjects.size; ++index ) {
            Rectangle collisionBox = gameObjects.get(index).getCollisionBox();
            boolean result = collisionBox.overlaps(worldClickRectangle);
            if (result) {
                setBlocksLightStatus(index);
                break;
            }
        }
        /*
        for (AbstractGameObject object : gameObjects) {
            Rectangle collisionBox = object.getCollisionBox();
            boolean result = collisionBox.overlaps(worldClickRectangle);
            if (result) {
                setBlockLight(object);
                break;
            }
        }
        */
    }

    private void setBlocksLightStatus(int index) {
        if (selectionType == SelectionType.CROSS) {
            setCrossLights(index);
        }
        else if ( selectionType == SelectionType.POINT) {
            setBlockLight(gameObjects.get(index));
        }

    }

    private void setCrossLights(int index) {
        // cross
        GameBlock currentBlock = getCurrentBlock(index);
        setCurrentLightStatus(currentBlock);

        // check neighbours
        // column = arrayindex % arraywidth
        // row = arrayindex / arraywidth
        int arrayWidth = cols;
        int column = index % arrayWidth;
        int row = index / arrayWidth;

        // check left neighbours
        if (column > 0) {
            currentBlock = getCurrentBlock(index - 1);
            setCurrentLightStatus(currentBlock);
        }
        if (column < arrayWidth - 1) {
            currentBlock = getCurrentBlock( index + 1);
            setCurrentLightStatus(currentBlock);
        }
        if (row > 0 ) {
            currentBlock = getCurrentBlock(index - arrayWidth);
            setCurrentLightStatus(currentBlock);
        }
        if (row < rows - 1) {
            currentBlock = getCurrentBlock(index + arrayWidth);
            setCurrentLightStatus(currentBlock);
        }
    }

    private void setCurrentLightStatus(GameBlock currentBlock) {
        boolean lightStatus = currentBlock.isLightOn();
        lightStatus = !lightStatus;
        currentBlock.setLightOn(lightStatus);
    }

    private GameBlock getCurrentBlock(int index) {
        return (GameBlock)gameObjects.get(index);
    }


    private void setBlockLight(AbstractGameObject object) {
        if (object instanceof GameBlock) {
            boolean lightStatus =  ((GameBlock) object).isLightOn();
            lightStatus = !lightStatus;
            ((GameBlock) object).setLightOn(lightStatus);
        }
    }
}

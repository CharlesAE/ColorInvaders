package com.colorinvaders.tween;

/**
 * Created by chuck on 6/10/15.
 */
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Align;
import com.colorinvaders.com.colorinvaders.Screen.Level1;

public class CustomAction extends TemporalAction {
    private float startX, startY;
    private float endX, endY;
    private int alignment = Align.bottomLeft;

    Level1 notifyInstance;
    float targetValue;

    public CustomAction (Level1 notifyInstance, float targetValue) {
        this.notifyInstance = notifyInstance;
        this.targetValue = targetValue;
    }

    protected void begin () {
        startX = target.getX(alignment);
        startY = target.getY(alignment);
    }

    protected void update (float percent) {
        float x = startX + (endX - startX) * percent;
        float y = startY + (endY - startY) * percent;
        target.setPosition(x, y, alignment);
        if (y <= targetValue) {
            notifyInstance.gameOver();
        }
    }

    public void reset () {
        super.reset();
        alignment = Align.bottomLeft;
    }

    public void setPosition (float x, float y) {
        endX = x;
        endY = y;
    }

    public void setPosition (float x, float y, int alignment) {
        endX = x;
        endY = y;
        this.alignment = alignment;
    }

    public float getX () {
        return endX;
    }

    public void setX (float x) {
        endX = x;
    }

    public float getY () {
        return endY;
    }

    public void setY (float y) {
        endY = y;
    }

    public int getAlignment () {
        return alignment;
    }

    public void setAlignment (int alignment) {
        this.alignment = alignment;
    }
}

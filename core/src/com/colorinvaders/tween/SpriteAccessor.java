package com.colorinvaders.tween;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by chuck on 6/6/15.
 */
public class SpriteAccessor implements TweenAccessor<Actor> {
    public static final int POS_XY = 1, Y=2, X=3;
    @Override
    public int getValues(Actor target, int tweenType, float[] returnValues) {

        switch(tweenType){
            case POS_XY:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2;
            case Y:
                returnValues[0] = target.getY();
                return 1;
            case X:
                returnValues[0] = target.getX();
                return 1;
            default:
                assert false;
                return -1;

        }
    }


    @Override
    public void setValues(Actor target, int tweenType, float[] newValues) {

        switch(tweenType){
            case POS_XY: target.setPosition(newValues[0], newValues[1]); break;
            case Y:
                target.setY(newValues[0]);
                break;
            case X:
                target.setX(newValues[0]);
                break;
            default:
                assert false;
        }
    }
}

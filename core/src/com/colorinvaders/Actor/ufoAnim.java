package com.colorinvaders.Actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by chuck on 6/5/15.
 */
public class ufoAnim extends Actor {
    TextureRegion region;
    private float time;
    public static TextureAtlas atlas;
    public static SpriteBatch batch;
    public static Animation tuts;

    public ufoAnim(){
        atlas = new TextureAtlas(Gdx.files.internal("img/ufo.atlas"));
        tuts = new Animation(0.05f, atlas.getRegions());
        tuts.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += delta;
        region = tuts.getKeyFrame(time);

    }
    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(region, this.getX(), this.getY());
    }
}


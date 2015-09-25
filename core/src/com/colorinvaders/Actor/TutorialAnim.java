package com.colorinvaders.Actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by chuck on 6/8/15.
 */
public class TutorialAnim extends Actor {
    TextureRegion region;
    private float time;
    public static TextureAtlas atlas;
    public static SpriteBatch batch;
    public static TextureRegion sc1, sc2,sc3;
    public static Animation tuts;

    public TutorialAnim(){
        atlas = new TextureAtlas(Gdx.files.internal("img/tuts.atlas"));
        sc1 = atlas.findRegion("tut1");
        sc2 = atlas.findRegion("tut2");
        sc3 = atlas.findRegion("tut3");

        tuts = new Animation(19f, sc1, sc2,sc3);
        tuts.setPlayMode(Animation.PlayMode.LOOP);
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

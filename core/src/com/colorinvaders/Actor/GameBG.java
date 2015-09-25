package com.colorinvaders.Actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by chuck on 6/10/15.
 */
public class GameBG extends Actor {
    Texture texture = new Texture(Gdx.files.internal("img/bg6.png"));
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture, 0, 0);
    }
}

package com.colorinvaders.Actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.colorinvaders.MainGame;

/**
 * Created by chuck on 6/7/15.
 */
public class GameTitle extends Actor {
    Texture texture = new Texture(Gdx.files.internal("img/title2.png"));
    @Override
    public void draw(Batch batch, float alpha){
        //batch.draw(texture,25, MainGame.HEIGHT-110);
        batch.draw(texture,25, MainGame.HEIGHT-175);
    }
}

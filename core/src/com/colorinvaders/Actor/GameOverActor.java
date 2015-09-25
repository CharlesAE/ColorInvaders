package com.colorinvaders.Actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by chuck on 6/7/15.
 */
public class GameOverActor extends Actor {
       Texture texture = new Texture(Gdx.files.internal("img/gameover.png"));
    public GameOverActor() {
        setBounds(getX(),getY(),texture.getWidth(),texture.getHeight());
    }
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,this.getX(),getY());
    }



}

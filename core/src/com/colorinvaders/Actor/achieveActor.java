package com.colorinvaders.Actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by chuck on 6/4/15.
 */
public class achieveActor extends Actor {
    Texture texture = new Texture(Gdx.files.internal("img/achievements.png"));
    public achieveActor(){
        setBounds(getX(),getY(),texture.getWidth(),texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture, this.getX(), this.getY());
    }
}

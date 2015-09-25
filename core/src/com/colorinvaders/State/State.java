package com.colorinvaders.State;

/**
 * Created by chuck on 6/9/15.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

//preferances for game count
public class State {
    Preferences prefs = Gdx.app.getPreferences("MyPreferences");


    public void setCount (int count) {
        prefs.putInteger("COUNT",count);
        prefs.flush();
    }

    public int getCount() {
        return prefs.getInteger("COUNT",0);
    }
}

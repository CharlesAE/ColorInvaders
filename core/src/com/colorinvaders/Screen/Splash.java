package com.colorinvaders.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.colorinvaders.MainGame;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by chuck on 6/3/15.
 */
public class Splash implements Screen {

    private final MainGame app;
    private Image splash;
    private Stage stage;
    Texture logotexture;


    public Splash(final MainGame app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, app.cam));
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                app.setScreen(app.mainMenuScreen);
            }
        };

        logotexture = new Texture (Gdx.files.internal("img/srs.png"));

        splash = new Image(logotexture);
        splash.setPosition((stage.getWidth() / 2) - splashImg.getWidth()/2, stage.getHeight()/2 - splashImg.getHeight()/2);
        splash.addAction(parallel(fadeOut(2.25f), run(transitionRunnable)));

        stage.addActor(splash);



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act(delta);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        stage.dispose();

    }
}

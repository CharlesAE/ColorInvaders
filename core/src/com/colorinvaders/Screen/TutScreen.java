package com.colorinvaders.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.colorinvaders.Actor.TutorialAnim;
import com.colorinvaders.Actor.GameBG;
import com.colorinvaders.Actor.GameTitle;
import com.colorinvaders.MainGame;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

/**
 * Created by chuck on 6/6/15.
 */
public class TutScreen implements Screen {

    private final MainGame app;

    public TextButton buttonPlay, buttonBack;
    private Skin menuSkin;
    private Stage stage;
    private BitmapFont ButtonFont;
    TutorialAnim tutorial = new TutorialAnim();
    GameBG gmbg = new GameBG();
    GameTitle gmtitle = new GameTitle();

    Runnable gotoMainMenu = new Runnable() {
        @Override
        public void run() {
            app.setScreen(app.mainMenuScreen);
        }
    };
    Runnable gotoLevel1 = new Runnable() {
        @Override
        public void run() {
            app.setScreen(app.Level1Screen);
        }
    };
    TweenManager tweenManager;
    private Sound menuSound;


    public TutScreen(final MainGame app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, app.cam));
        this.ButtonFont = app.buttonFont;
        this.menuSkin = new Skin(app.menuButtonAtlas);
        this.menuSound = app.innerMenuSound;
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        stage.clear();
        stage.addActor(gmbg);
        stage.addActor(gmtitle);
        app.actionResolver.bannerAdvert();
        if (app.actionResolver.isSignedIn())
        {
            app.actionResolver.unlockAchievement("------------------");
        }
        initNavigationButtons();
        initTutorialAni();
      
        
    }


    private void initNavigationButtons() {

        //omitted
    }

	//tutorial animation
    public void initTutorialAni(){

        tutorial.setPosition(15, 180);
        tutorial.addAction(sequence(Actions.hide(), fadeIn(0.5f, Interpolation.pow2),parallel( scaleTo(2f, 2f, 2.5f, Interpolation.pow5), Actions.show())));
        stage.addActor(tutorial);

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();

    }

    private void update(float delta){
        stage.act(delta);
        tweenManager.update(delta);
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

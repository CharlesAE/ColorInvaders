package com.colorinvaders.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.colorinvaders.Actor.GameBG;
import com.colorinvaders.Actor.GameTitle;
import com.colorinvaders.Actor.achieveActor;
import com.colorinvaders.Actor.leaderActor;
import com.colorinvaders.MainGame;
import com.colorinvaders.tween.ActorAccessor;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by chuck on 6/4/15.
 */
public class Highscores implements Screen {


	//Variables
    private final MainGame app;
    private Skin menuSkin;
    private Label heading, score;
    private int sc;
    private Table table;
    private Stage stage;
    private BitmapFont ButtonFont, headerFont, textFont;
    private TextButton buttonBack;
    private TweenManager tweenManager;
    private Sound menuSound,innerMenuSound;
    GameBG gmbg = new GameBG();
    leaderActor leader = new leaderActor();
    achieveActor achieve = new achieveActor();
    GameTitle gameTitle = new GameTitle();


    Runnable transitionRunnable = new Runnable() {
        @Override
        public void run() {
            stage.clear();
            app.setScreen(app.mainMenuScreen);
        }
    };
    Runnable transitionGlobal = new Runnable() {
        @Override
        public void run() {
            if (app.actionResolver.isSignedIn())
            {
                app.actionResolver.showScores();
            }
        }
    };
    Runnable transitionAchievement = new Runnable() {
        @Override
        public void run() {
            if (app.actionResolver.isSignedIn())
            {
                app.actionResolver.showAchievement();
            }
        }
    };

	//Highscores constructor
    public Highscores(final MainGame app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, app.cam));
        this.menuSkin = new Skin(app.menuButtonAtlas);
        this.ButtonFont = app.buttonFont;
        this.headerFont = app.sigFont;
        this.textFont = app.leaderFont;
        this.menuSound = app.mainMenuClickSound;
        this.innerMenuSound = app.innerMenuSound;
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        stage.clear();
        Label.LabelStyle headStyle = new Label.LabelStyle(headerFont, Color.WHITE);

        app.actionResolver.bannerAdvert();
        heading = new Label("HIGHSCORES", headStyle);
        heading.setPosition(app.cam.viewportWidth / 2F - heading.getWidth() / 2, 550);


        stage.addActor(gmbg);
        stage.addActor(gameTitle);
        stage.addActor(heading);


        initTable();

        initNavButtons();
        initTween();
    }

    private void initTable(){
	//Table showing the top 5 scores
        table = new Table();


	//scores are red, using "textFont" from MainGame
        Label.LabelStyle textStyle = new Label.LabelStyle(textFont, Color.RED);


	//table bounds and positioning are set 
        table.setBounds(app.cam.viewportWidth / 2F - table.getWidth() / 2, 400, 0, 0);
        table.addAction(Actions.sequence(Actions.hide(), Actions.fadeIn(0.25f), Actions.show()));

	//tables populated with scores
        for(int i=1;i<=5;i++){
            score=new Label(i+". " + app.saveManager.loadDataValue("Score"+i, int.class), textStyle);
            table.add(score).padBottom(2).row();
            sc = app.saveManager.loadDataValue("Score1", int.class);
            app.actionResolver.submitScore(sc);

        }

	//table with high scores is added to stage
        stage.addActor(table);
    }


    private void initTween(){
        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAccessor());

        //heading font color animation, toggles between yellow and white
        Timeline.createSequence().beginSequence()
                .push(Tween.to(heading, ActorAccessor.RGB, 1.5f).target(1, 1, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, 1.5f).target(1, 1, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, 1.5f).target(1, 1, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, 1.5f).target(1, 1, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, 1.5f).target(1, 1, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, 1.5f).target(1, 1, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, 1.5f).target(1, 1, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, 1.5f).target(1, 1, 1))
                .end().repeat(Tween.INFINITY, 0).start(tweenManager);

        Tween.to(buttonBack, ActorAccessor.X, 0.85f).target(app.cam.viewportWidth / 2F - buttonBack.getWidth() / 2).start(tweenManager);

        tweenManager.update(Gdx.graphics.getDeltaTime());
    }

    private void initNavButtons() {

	//button properties
        TextButton.TextButtonStyle backStyle= new TextButton.TextButtonStyle();
	//buttons are yellow by default, pink when pressed
        backStyle.up = menuSkin.getDrawable("default-yellow");
        backStyle.down = menuSkin.getDrawable("default-pink");
       


	//back button creationg and positioning
        buttonBack = new TextButton("    BACK     ", backStyle);
        buttonBack.setPosition(-200, 100);

	//back button listener
        buttonBack.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                innerMenuSound.play(0.5f);
                app.actionResolver.hideAd();
                buttonBack.addAction(parallel(scaleTo(2f, 2f, 1f, Interpolation.pow5), moveTo(-200, 100, 0.85f, Interpolation.swing)));
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttonBack.addAction(sequence(Actions.delay(1f), run(transitionRunnable)));
            }
        });



	//leaderboard button positioning
        leader.setPosition((app.cam.viewportWidth / 2) + 40, 200);
        leader.addAction(Actions.sequence(Actions.hide(), Actions.fadeIn(1f), Actions.show()));
	
	//leaderboard button listener
        leader.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                menuSound.play();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leader.addAction(run(transitionGlobal));
            }
        });


	//achievements button positioning
        achieve.setPosition((app.cam.viewportWidth / 2) - 130, 200);
        achieve.addAction(Actions.sequence(Actions.hide(), Actions.fadeIn(1f), Actions.show()));


	//achievements button listener
        achieve.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                menuSound.play();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                achieve.addAction(run(transitionAchievement));
            }
        });


	

	//Achievement and Leaderboard buttons are added ONLY if user is signed in to Google Play
        if (app.actionResolver.isSignedIn()) {
            stage.addActor(achieve);
            stage.addActor(leader);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        tweenManager.update(delta);
        stage.draw();
    }

    private void update(float delta){
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

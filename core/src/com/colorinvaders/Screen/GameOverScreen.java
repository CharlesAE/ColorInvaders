package com.colorinvaders.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.colorinvaders.Actor.Clouds;
import com.colorinvaders.Actor.GameOverActor;
import com.colorinvaders.Actor.GameBG;
import com.colorinvaders.Actor.RateIcon;
import com.colorinvaders.Actor.ShareIcon;
import com.colorinvaders.Actor.achieveActor;
import com.colorinvaders.Actor.leaderActor;
import com.colorinvaders.MainGame;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.colorinvaders.State.State;
import com.colorinvaders.tween.SpriteAccessor;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by chuck on 6/3/15.
 */
public class GameOverScreen implements Screen {

    private final MainGame app;

    public Stage stage;
    public TextButton buttonNew, buttonBack;
    public Table tableBack;
    private Skin menuSkin;
    Label currentScore, topScore;
    private Sound menuSound;


    GameBG gbg = new GameBG();
    leaderActor leader = new leaderActor();
    achieveActor achieve = new achieveActor();
    ShareIcon share = new ShareIcon();
    RateIcon rate = new RateIcon();
    Clouds cloud;
    private TweenManager tweenManager;


	
    

    

    public GameOverScreen(MainGame app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, app.cam));
        this.menuSkin = new Skin(app.menuButtonAtlas);
        this.menuSound = app.mainMenuClickSound;
    }
    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
        stage.clear();
        initCloud();
        gameOver();

	//interstitial ad loaded and/or displayed
            app.actionResolver.showOrLoadInterstital();

    }

    public void gameOver() {

	//table to store buttons

        tableBack = new Table();

        TextButton.TextButtonStyle menuStyle= new TextButton.TextButtonStyle();
        menuStyle.up = menuSkin.getDrawable("default-red");
        menuStyle.down = menuSkin.getDrawable("default-green");
       
	//'play again' button initialization and positioning
        buttonNew = new TextButton("PLAY AGAIN", menuStyle);
        buttonNew.setSize(200, 50);
	//'play again' button listener
        buttonNew.addListener(new InputListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                menuSound.play();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int butotn) {
                app.setScreen(app.Level1Screen);
            }
        });


	//back button
        buttonBack = new TextButton("MAIN MENU", menuStyle);
        buttonBack.setSize(200, 50);

        buttonBack.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                menuSound.play();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                app.setScreen(app.mainMenuScreen);
            }
        });


	//global leaderboards button
        leader.setPosition((app.cam.viewportWidth / 2) +110, 80);
        leader.addAction(Actions.sequence(Actions.hide(), Actions.fadeIn(1.25f), Actions.show()));
        
	leader.addListener(new InputListener() {
            
            //omitted
        });


	//achievements button
        achieve.setPosition((app.cam.viewportWidth / 2) - 200, 80);
        achieve.addAction(Actions.sequence(Actions.hide(), Actions.fadeIn(1.25f), Actions.show()));
       
	achieve.addListener(new InputListener() {
            //omitted
        }

	//'rate game' button
        rate.setPosition((app.cam.viewportWidth / 2) + 10, 80);
        rate.addAction(Actions.sequence(Actions.hide(), Actions.fadeIn(1.25f), Actions.show()));
        rate.addListener(new InputListener() {
		//omitted
            }

	//'share game' button
        share.setPosition((app.cam.viewportWidth / 2) - 100, 80);
        share.addAction(Actions.sequence(Actions.hide(), Actions.fadeIn(1.25f), Actions.show()));
        share.addListener(new InputListener() {
            	//omitted
		}



        GameOverActor gOver = new GameOverActor();
        stage.addActor(gbg);
        
       
	Label.LabelStyle headStyle = new Label.LabelStyle(app.scoreFont, Color.RED);
	//score is passed obtained from
        currentScore=new Label("Score: " + app.save2.loadDataValue("NScore", int.class), headStyle);
        currentScore.addAction(Actions.sequence(Actions.hide(), Actions.fadeIn(1.75f), Actions.show()));
	//top score is passed from leaderboard
        topScore=new Label("Top Score: " + app.saveManager.loadDataValue("Score1", int.class), headStyle);
        topScore.addAction(Actions.sequence(Actions.hide(), Actions.fadeIn(1.75f), Actions.show()));

        
	tableBack.add(currentScore);
        tableBack.add(topScore).padLeft(5).row();
        stage.addActor(tableBack);



    }
    private void initCloud(){

            cloud = new Clouds();
            cloud.setPosition(-450,350);
        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new SpriteAccessor());
        Timeline.createSequence().beginSequence()
                .push(Tween.from(cloud, SpriteAccessor.X, 8f).target(-450))
                .push(Tween.to(cloud, SpriteAccessor.X, 8f).target(450))
                .end().repeat(Tween.INFINITY, 2f).start(tweenManager);
        tweenManager.update(Gdx.graphics.getDeltaTime());
        stage.addActor(cloud);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    public void update(float delta) {

        stage.act(delta);
        tweenManager.update(delta);
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

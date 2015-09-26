package com.colorinvaders.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.colorinvaders.Actor.GameTitle;
import com.colorinvaders.Actor.RateIcon;
import com.colorinvaders.Actor.ShareIcon;
import com.colorinvaders.Actor.logoActor;
import com.colorinvaders.MainGame;
import com.colorinvaders.Actor.GameBG;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;


/**
 * Created by chuck on 6/4/15.
 * Main menu
 */
public class MainMenu implements Screen {

    private final MainGame app;

	//main menu constructor
    public MainMenu(final MainGame app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, app.cam));
        this.menuSound = app.mainMenuClickSound;
        this.innerMenuSound = app.mainMenuLoadSound;
        this.ButtonFont = app.menuButtonFont;
        this.skin = new Skin(app.menuButtonAtlas);
    }

	//variables
    private Sound menuSound, innerMenuSound;

    private Stage stage;
    private Skin  skin;
    BitmapFont ButtonFont;
    private TextButton buttonPlay,buttonExit, buttonTutorial, buttonScores;
    GameBG gmbg = new GameBG();
    GameTitle gmTitle = new GameTitle();
    ShareIcon share = new ShareIcon();
    RateIcon rate = new RateIcon();
    logoActor logo = new logoActor();

    
Runnable sharegame = new Runnable() {
        @Override
        public void run() {

                app.actionResolver.shareGame();
        }
    };
    Runnable rategame = new Runnable() {
        @Override
        public void run() {

            app.actionResolver.rateGame();
        }
    };
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        initTable();
        //inner menu sound goes here
        stage.addActor(gmbg);
        stage.addActor(gmTitle);
        stage.addActor(buttonPlay);
        stage.addActor(rate);
        stage.addActor(share);
        stage.addActor(buttonTutorial);
        stage.addActor(buttonExit);
        stage.addActor(buttonScores);
        stage.addActor(logo);
 
   }

	//main menu table
    private void initTable(){
        

        //creates play button properties
        TextButton.TextButtonStyle playStyle= new TextButton.TextButtonStyle();
        playStyle.up = skin.getDrawable("default-red");
        playStyle.down = skin.getDrawable("default-pink");
        playStyle.pressedOffsetX = 2;
        playStyle.pressedOffsetY = -2;
        playStyle.font = ButtonFont;


	//'play' button initialization and positioning
        buttonPlay = new TextButton("PLAY", playStyle);
        buttonPlay.setWidth(300f);
        buttonPlay.setHeight(70f);
        buttonPlay.setPosition(app.cam.viewportWidth / 2F - buttonPlay.getWidth() / 2, -400);

	//'play button' listener
        buttonPlay.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //menu sound goes here

                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                app.setScreen(app.Level1Screen);
            }
        });



	//exit button properties
        TextButton.TextButtonStyle exitStyle= new TextButton.TextButtonStyle();
        exitStyle.up = skin.getDrawable("default-red");
        exitStyle.down = skin.getDrawable("default-round-down");
	//exit button positioning and initialization
        buttonExit = new TextButton("EXIT", exitStyle);
        buttonExit.setWidth(300f);
        buttonExit.setHeight(70f);
        buttonExit.setPosition(app.cam.viewportWidth / 2F - buttonExit.getWidth() / 2, -400);

        buttonExit.addListener(new InputListener() {
	//omitted
	}


    

	//tutorial button properties
	TextButton.TextButtonStyle tutStyle= new TextButton.TextButtonStyle();
        tutStyle.up = skin.getDrawable("default-red");
        tutStyle.down = skin.getDrawable("default-green");
	//tutorial button initialization and positioning
        buttonTutorial = new TextButton("TUTORIAL", tutStyle);
        buttonTutorial.setWidth(300f);
        buttonTutorial.setHeight(70f);
        buttonTutorial.setPosition(app.cam.viewportWidth / 2F - buttonTutorial.getWidth() / 2, -400);
	//tutorial button listener
        buttonTutorial.addListener(new InputListener() {
	//omitted
        }



	//highscores button properties
	TextButton.TextButtonStyle scoreStyle= new TextButton.TextButtonStyle();
        scoreStyle.up = skin.getDrawable("default-red");
        scoreStyle.down = skin.getDrawable("default-teal");
 	//highscores initialization and positioning
        buttonScores = new TextButton("SCORES", scoreStyle);
        buttonScores.setWidth(300f);
        buttonScores.setHeight(70f);
        buttonScores.setPosition(app.cam.viewportWidth / 2F - buttonScores.getWidth() / 2, -400);
	//highscores button listener
        buttonScores.addListener(new InputListener() {
	//omitted
        }

	
	//rate game button positioning
        rate.setPosition((app.cam.viewportWidth/2)+65, -400);
	//rate game button listener
        rate.addListener(new InputListener() {
	//omitted
	}


	//share game button positioning
        share.setPosition((app.cam.viewportWidth/2)-155, -400);
	//share game button listener
        share.addListener(new InputListener() {
	//omitted
	}

	logo.setPosition((app.cam.viewportWidth / 2) - (logo.getWidth()/2),-400);
    }


    

    private void update(float delta) {
        stage.act(delta);
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

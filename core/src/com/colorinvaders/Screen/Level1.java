package com.colorinvaders.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.colorinvaders.Actor.GameBG;
import com.colorinvaders.Actor.Ufo;
import com.colorinvaders.MainGame;
import com.colorinvaders.State.State;
import com.colorinvaders.tween.CustomAction;
import com.colorinvaders.tween.SpriteAccessor;


import java.util.Random;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Back;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by chuck on 6/6/15.
 */

public class Level1 implements Screen {

    MainGame app;
    private Stage stage;
    private Skin trueFalseSkin;


	//constructor, takes in MainGame

    public Level1(MainGame app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, app.cam));
        this.trueFalseSkin = new Skin(app.trueFalseAtlas);
        this.TextColor = app.TextColor;
        this.score = app.score;
        this.ColorArray = app.ColorArray;
        this.trueSound = app.pointSound;
        this.endSound = app.gameOverSound;
        this.coinSound = app.coinSound;
    }


	//variables

    private Sound trueSound, endSound, coinSound, ufoTrans;
    private String[] TextColor;

    private Color[] ColorArray;
    private String myText;
    public int id1, id2, score, gameLevel;
    private Ufo ufo;
    float dropSpeed;
    private TextButton btnT, btnF;
    private TweenManager tweenManager;
    private Label scHead, lvHead;
    public Label texttest;
    private Label.LabelStyle textStyle = new Label.LabelStyle();
    private Label.LabelStyle headStyle = new Label.LabelStyle();
    GameBG gmbg = new GameBG();
    State preferences=new State();


	Runnable gotoLevel2 = new Runnable() {
                    @Override
                    public void run() {
                        app.setScreen(app.Level2Screen);
                    }
                };


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);


        Gdx.input.setCatchBackKey(true);
        stage.clear();
        startGame();

        initUfo();
        initButtons();

        gameCount();

        //some code omitted

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	//back button sends game to mainscreen
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            app.setScreen(app.mainMenuScreen);

        }
        update(delta);
        stage.draw();
    }



	//Game counter for achievement unlocks
    private void gameCount(){

	//if user is logged in, game counter is initialized.
        if (app.actionResolver.isSignedIn())
        {
        int count=preferences.getCount();

        //now set the count
        preferences.setCount(count+1);

	//achivements are unlocked when game is played
        switch(count){
            case 5:
                app.actionResolver.unlockAchievement("------------------");
                break;
            case 25:
                app.actionResolver.unlockAchievement("------------------");
                break;
            case 50:
                app.actionResolver.unlockAchievement("------------------");
                break;
            case 100:
                app.actionResolver.unlockAchievement("------------------");
                break;
        }
        }
    }

	/*Level 1 set up and positioning
	A random text and color is generated and displayed at central position*/

    private void startGame(){
        if (app.actionResolver.isSignedIn())
        {
            app.actionResolver.unlockAchievement("------------------");
        }
	
	//Text is randomly generated
        id1 = new Random().nextInt(TextColor.length);

	//color is randomly generated	
        id2 = new Random().nextInt(ColorArray.length);
	
	//randomly generated index is passed into text array
        myText = TextColor[id1];
        texttest = new Label(myText,textStyle);
	//randomly generated index is passed into color array
        texttest.setColor(ColorArray[id2]);

	//position is set to middle of screen
        texttest.setPosition(app.cam.viewportWidth / 2F - texttest.getWidth() / 2, app.cam.viewportHeight / 2F - texttest.getHeight() / 2);
    }

    //Generates new text and color
    private void setupColor(){

        texttest.remove();
        scHead.remove();
        ufo.remove();

        
        stage.addActor(exp);
        id1 = new Random().nextInt(TextColor.length);
        id2 = new Random().nextInt(ColorArray.length);

        
        myText = TextColor[id1];
        texttest = new Label(myText, textStyle);
        texttest.setColor(ColorArray[id2]);
        texttest.setPosition(app.cam.viewportWidth / 2F - texttest.getWidth() / 2, app.cam.viewportHeight - 110);

	//word falls, and check is implemented to see if word falls to bottom of screen
        CustomAction wordDrop = new CustomAction(this, 10);
        wordDrop.setPosition(app.cam.viewportWidth / 2F - texttest.getWidth() / 2, 5);
        wordDrop.setDuration(dropSpeed);
        texttest.addAction(wordDrop);
        stage.addActor(texttest);




	//speed of word increases with score
        switch(score){
            case 5:
                dropSpeed -=1.25;
                break;
            case 10:
                if (app.actionResolver.isSignedIn()) {
                    app.actionResolver.unlockAchievement("------------------");
                }
                dropSpeed -=1;
                break;
            case 15:
                dropSpeed -=1;
                break;
            case 25:
                if (app.actionResolver.isSignedIn()) {
                    app.actionResolver.unlockAchievement("------------------");
                }
                 dropSpeed -=0.85;
                break;
            case 30:
                dropSpeed -=1.25;
                break;
            case 40:
                dropSpeed -=0.75;
                break;
            case 50:
                if (app.actionResolver.isSignedIn()) {
                    app.actionResolver.unlockAchievement("------------------");
                }

                break;
        }

        stage.addActor(scHead);
    }


    private void update(float delta) {
        stage.act(delta);
        tweenManager.update(delta);
    }

    private void initUfo(){
	//omitted
}


		//button initialization
    private void initButtons() {


	//button 'true' properties
        TextButton.TextButtonStyle buttonFalse = new TextButton.TextButtonStyle();
        buttonFalse.up = trueFalseSkin.getDrawable("btnFalse");
        buttonFalse.down = trueFalseSkin.getDrawable("btnFalse_p");
        buttonFalse.pressedOffsetX = 2;
        buttonFalse.pressedOffsetY = -2;
        buttonFalse.font = app.tfFont;

	//button 'false' properties
        TextButton.TextButtonStyle buttonTrue = new TextButton.TextButtonStyle();
        buttonTrue.up = trueFalseSkin.getDrawable("btnTrue");
        buttonTrue.down = trueFalseSkin.getDrawable("btnTrue_p");
        buttonTrue.pressedOffsetX = 2;
        buttonTrue.pressedOffsetY = -2;
        buttonTrue.font = app.tfFont;


	//button 'true' initialization and positioning
        btnT = new TextButton("TRUE", buttonTrue);
        btnT.setPosition(270, 15);
	//button 'true' listener
        btnT.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		//check for true
                checkTrue();
            }
        });

	//button 'false' initialization and positioning
        btnF = new TextButton("FALSE", buttonFalse);
        btnF.setPosition(0, 15);

	//button 'false' listener
        btnF.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		//check for false
                checkFalse();
            }
        });
    }

    public void checkFalse(){
	//disable button 'true' if false has been pressed
        btnT.setDisabled(true);
	
	//checks if index of text array is not equal to index of color array
        if(id1 != id2){
	//increment score and play accompanying sound
            score++;
            trueSound.play(0.5f);
            coinSound.play(0.3f);

	//generate new word
            setupColor();
        }

        else
        if(id1 == id2){
	//if text array and color array indices are a match, game over
            gameOver();

        }
    }

    public void checkTrue(){
	
        btnF.setDisabled(true);
        if(id1 == id2) {
            score ++;
            trueSound.play(0.5f);
            coinSound.play(0.3f);
            setupColor();
        }
        else
        if(id1 != id2){
            gameOver();
        }
    }

    public void gameOver() {

	//if user logged in to google play, submit score to global leaderboard
        if (app.actionResolver.isSignedIn())
        {
            app.actionResolver.submitScore(score);

        }
	//submits score to local leaderboard
        checkAndStoreScore();
        endSound.play(0.5f);
        app.setScreen(app.gameOverScreen);
    }


	//score submitted to local leaderboard
    private void checkAndStoreScore() {

        app.save2.saveDataValue("NScore", score);
        int finalScore=(int)(score);
        int lowestScore=app.saveManager.loadDataValue("Score5", int.class);

	//loops to determine if score is eligable to be added to local leaderboards, and inserts it into correct position if it is
        if(finalScore>lowestScore) {
            int[] scores = new int[5];
            for (int i = 1; i <= 5; i++) {
                scores[i - 1] = app.saveManager.loadDataValue("Score" + i, int.class);
            }
            scores[4] = finalScore;
            for (int i = 4; i > 0; i--) {
                if (scores[i] > scores[i - 1]) {
                    finalScore = scores[i - 1];
                    scores[i - 1] = scores[i];
                    scores[i] = finalScore;
                } else {
                    break;
                }
            }

            for(int i=1;i<=5;i++){
                app.saveManager.saveDataValue("Score"+i, scores[i-1]);
            }
        }
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
        trueFalseSkin.dispose();
    }
}

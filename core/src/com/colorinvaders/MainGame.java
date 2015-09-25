package com.colorinvaders;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.colorinvaders.Services.ActionResolver;
import com.colorinvaders.State.State;
import com.colorinvaders.Screen.EndScreen;
import com.colorinvaders.Screen.GameOverScreen;
import com.colorinvaders.Screen.Highscores;
import com.colorinvaders.Screen.MainMenu;
import com.colorinvaders.Screen.Splash;
import com.colorinvaders.Screen.Level1;
import com.colorinvaders.Screen.TutScreen;

/**
 * Created by chuck on 6/4/15.
 * Main Game Manager, where global variables are declared
 */
public class MainGame extends Game {


	public ActionResolver actionResolver;
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public SpriteBatch batch;
	public TextureAtlas menuButtonAtlas;
	public TextureAtlas trueFalseAtlas;
	public BitmapFont buttonFont, wordFont, sigFont, tfFont, scoreFont, menuButtonFont, leaderFont;
	public int score;
	//Words used
	public String[] TextColor = {"RED  ","PURPLE","GREEN","SILVER","ORANGE","BLACK","WHITE","BLUE","PINK"};
	//colors used
	public Color[] ColorArray = {Color.RED,new Color(0.50f, 0f, 0.50f, 1), Color.GREEN, new Color(0.60f, 0.60f, 0.60f, 1), new Color(1, 0.69f, 0, 1),new Color(0, 0, 0, 0.85f),Color.WHITE,Color.BLUE,Color.MAGENTA};
	public OrthographicCamera cam;
	public GameOverScreen gameOverScreen;
	public Splash splashScreen;
	public MainMenu mainMenuScreen;
	public Highscores scoreMenu;
	public Level1 Level1Screen;
        public TutScreen tutScreen;
	public SaveManager saveManager, save2;
	public Sound mainMenuClickSound, innerMenuSound, pointSound, coinSound, gameOverSound, mainMenuLoadSound, tfTrans, playMenuSound, ufoTrans;
	public Music alarmSound;



	public MainGame(ActionResolver actionResolver) {
		super();
		this.actionResolver = actionResolver;
	}


	@Override
	public void create () {

		cam = new OrthographicCamera();
		cam.setToOrtho(false, WIDTH, HEIGHT);

		menuButtonAtlas = new TextureAtlas("ui/uiskin.atlas");
		trueFalseAtlas = new TextureAtlas("ui/false.atlas");
		batch = new SpriteBatch();

	//sounds
		innerMenuSound = Gdx.audio.newSound(Gdx.files.internal("sound/menu.ogg"));
		playMenuSound = Gdx.audio.newSound(Gdx.files.internal("sound/playmenu.ogg"));
		explodeSound = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.wav"));
		mainMenuClickSound = Gdx.audio.newSound(Gdx.files.internal("sound/mainmenu.ogg"));
		pointSound = Gdx.audio.newSound(Gdx.files.internal("sound/point.ogg"));
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sound/fail.ogg"));
		mainMenuLoadSound = Gdx.audio.newSound(Gdx.files.internal("sound/transition.ogg"));
		tfTrans = Gdx.audio.newSound(Gdx.files.internal("sound/tftrans.ogg"));
		coinSound = Gdx.audio.newSound(Gdx.files.internal("sound/coin.ogg"));

	//fonts used in game
		initFonts();

	//screens used
		splashScreen = new Splash(this);
		tutScreen = new TutScreen(this);
		mainMenuScreen = new MainMenu(this);
		Level1Screen = new Level1(this);
		gameOverScreen = new GameOverScreen(this);
		scoreMenu = new Highscores(this);
		endingScreen = new EndScreen(this);

	//Splash screen set as 1st screen
		this.setScreen(splashScreen);
		prepareLocalScores();
		newScores();
		actionResolver.hideAd();
	}


	private void newScores() {
		save2=new SaveManager(true);//first run
		if(save2.loadDataValue("NScore", int.class)==null) {

			for(int j=1;j<=5;j++) {
				save2.saveDataValue("NScore"+j, 0);
		}

		}


	}

	private void prepareLocalScores() {
		saveManager=new SaveManager(true);
		if(saveManager.loadDataValue("Score1", int.class)==null){//first run

			for(int i=1;i<=5;i++) {
				saveManager.saveDataValue("Score"+i, 0);
			}
		}
	}


	private void initFonts(){
		FreeTypeFontGenerator gen1 = new FreeTypeFontGenerator(Gdx.files.internal("font/vac.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter para1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		para1.size = 35;
		buttonFont = gen1.generateFont(para1);

		FreeTypeFontGenerator gen2 = new FreeTypeFontGenerator(Gdx.files.internal("font/vac.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter para2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		para2.size = 50;
		wordFont = gen2.generateFont(para1);


		FreeTypeFontGenerator gen3 = new FreeTypeFontGenerator(Gdx.files.internal("font/vac.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter para3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		para3.size = 50;
		sigFont = gen3.generateFont(para3);

		FreeTypeFontGenerator gen4 = new FreeTypeFontGenerator(Gdx.files.internal("font/june.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter para4 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		para4.size = 40;
		para4.color = Color.WHITE;
		tfFont = gen4.generateFont(para4);

		FreeTypeFontGenerator gen5 = new FreeTypeFontGenerator(Gdx.files.internal("font/june.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter para5 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		para5.size = 30;
		scoreFont = gen5.generateFont(para5);

		FreeTypeFontGenerator gen6 = new FreeTypeFontGenerator(Gdx.files.internal("font/vac.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter para6 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		para6.size = 50;
		menuButtonFont = gen6.generateFont(para6);

		FreeTypeFontGenerator gen7 = new FreeTypeFontGenerator(Gdx.files.internal("font/june.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter para7 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		para7.size = 35;
		leaderFont = gen7.generateFont(para7);



	}
	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		innerMenuSound.dispose();
		pointSound.dispose();
		gameOverSound.dispose();
		playMenuSound.dispose();
		coinSound.dispose();
		alarmSound.dispose();
		explodeSound.dispose();
		crashSound.dispose();
		tfTrans.dispose();
		ufoTrans.dispose();
		mainMenuClickSound.dispose();
		mainMenuLoadSound.dispose();
		mainMenuScreen.dispose();
		wordFont.dispose();
		buttonFont.dispose();
		menuButtonFont.dispose();
		leaderFont.dispose();
		sigFont.dispose();
		scoreFont.dispose();
		scoreMenu.dispose();
		scoreMenu.hide();
        	tutScreen.dispose();
		tfFont.dispose();
		splashScreen.dispose();
		Level1Screen.dispose();
		Level1Screen.hide();


	}

	@Override
	public void render() {
		super.render();
	}



}

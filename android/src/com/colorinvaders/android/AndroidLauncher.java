package com.colorinvaders.android;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.colorinvaders.MainGame;
import com.colorinvaders.Services.ActionResolver;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;
import com.google.example.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements GameHelper.GameHelperListener, ActionResolver{
	protected GameHelper gameHelper;
	protected InterstitialAd interstitialAd;
	protected AdView adView, admobView;
	protected View gameView;
	private static final String GAME_NAME = "Color Invaders";
	private String SHARE_MESSAGE = "Check out " + GAME_NAME + " %s";
	private String url = "https://play.google.com/store/apps/details?id=com.colorinvaders.android";
	private String message = String.format(SHARE_MESSAGE, url);

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		RelativeLayout layout = new RelativeLayout(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);


		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.setup(this);

		View gameView = createGameView(config);
		layout.addView(gameView);


		admobView = createAdView();
		layout.addView(admobView);


		setContentView(layout);

		//startAdvertising(admobView);


		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(getInterstitialUnitId());
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {

			}

			@Override
			public void onAdClosed() {


			}
		});
	}

	private AdView createAdView() {
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId(getBannerUnitId());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		adView.setLayoutParams(params);
		adView.setBackgroundColor(Color.BLACK);

		return adView;
	}

	private View createGameView(AndroidApplicationConfiguration config) {
		gameView = initializeForView(new MainGame(this), config);
		return gameView;
	}

	@Override
	public void bannerAdvert(){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				startAdvertising(admobView);
				admobView.setVisibility(View.VISIBLE);

			}
		});
	}


	@Override
	public void rateGame(){
		if (isSignedIn() == true) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addCategory(Intent.CATEGORY_BROWSABLE);
			intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.colorinvaders.android"));
			startActivity(intent);

		}

	}

	@Override
	public void shareGame(){
		 {
			Intent share = new Intent(Intent.ACTION_SEND);

			 share.setType("text/plain");
			 share.putExtra(Intent.EXTRA_TEXT, message);
			startActivity(Intent.createChooser(share,"Share Color Invaders" ));

		}

	}

	@Override
	public void hideAd(){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				admobView.setVisibility(View.GONE);

			}
		});
	}


	private void startAdvertising(AdView adView) {
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}



	private String getBannerUnitId() {
		return getString(R.string.real_banner_ad_id);
	}

	private String getInterstitialUnitId() {
		return getString(R.string.real_interstitial_ad_id);
	}


	@Override
	public void onStart(){
		super.onStart();
		gameHelper.onStart(this);

	}

	@Override
	public void onStop(){
		super.onStop();
		gameHelper.onStop();

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public void onSignInFailed() {

	}

	@Override
	public void onSignInSucceeded() {

	}

	@Override
	public void signIn() {
		try
		{
			runOnUiThread(new Runnable()
			{
				//@Override
				public void run()
				{
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				//@Override
				public void run()
				{
					gameHelper.signOut();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}



	@Override
	public void submitScore(int score) {
		if (isSignedIn() == true)
		{
			Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_id), score);
		}

	}

	@Override
	public void showScores() {
		if (isSignedIn() == true) {
			startActivityForResult(
					Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), getString(R.string.leaderboard_id)), 10);
		}
	}

	@Override
	public void showAchievement(){
		if (isSignedIn() == true) {
			startActivityForResult(
					Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 25);
		}
	}

	@Override
	public void unlockAchievement(final String achievementId){
		if (isSignedIn() == true) {
			Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
		}
	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void showOrLoadInterstital() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					if (interstitialAd.isLoaded()) {
						interstitialAd.show();
						//Toast.makeText(getApplicationContext(), "Showing Interstitial", Toast.LENGTH_SHORT).show();
					}
					else {
						AdRequest interstitialRequest = new AdRequest.Builder().build();
						interstitialAd.loadAd(interstitialRequest);
						//Toast.makeText(getApplicationContext(), "Loading Interstitial", Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (Exception e) {
		}
	}

}

package com.colorinvaders.Services;

/**
 * Created by chuck on 6/15/15.
 */
public interface ActionResolver {
    public void signIn();
    public void showOrLoadInterstital();
    public void signOut();
    public void bannerAdvert();
    public void rateGame();
    public void shareGame();
    public void hideAd();
    public void submitScore(int score);
    public void showScores();
    public void showAchievement();
    public void unlockAchievement(String achievementId);
    public boolean isSignedIn();
}

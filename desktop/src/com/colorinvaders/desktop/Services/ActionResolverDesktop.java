package com.colorinvaders.desktop.Services;

import com.colorinvaders.Services.ActionResolver;

/**
 * Created by chuck on 8/10/15.
 */
public class ActionResolverDesktop implements ActionResolver {

    @Override
    public void signIn()
    {
        System.out.println("DesktopGoogleServices: signIn()");
    }



    @Override
    public void signOut()
    {
        System.out.println("DesktopGoogleServices: signOut()");
    }



    @Override
    public void submitScore(int score)
    {
        System.out.println("DesktopGoogleServices: submitScore(" + score + ")");
    }

    @Override
    public void showScores()
    {
        System.out.println("DesktopGoogleServices: showScores()");
    }
    @Override
    public void unlockAchievement(String achievementId) {
        System.out.println("unlockAchievement " + achievementId);
    }

    @Override
    public void showAchievement(){
        System.out.println("DesktopGoogleServices:: showAchievement()" );
    }

    @Override
    public void rateGame(){
        System.out.println("DesktopGoogleServices:: RateGame()" );
    }

    @Override
    public void shareGame(){
        System.out.println("DesktopGoogleSerivces:: ShareGame()");
    }

    @Override
    public void showOrLoadInterstital() {
        System.out.println("showOrLoadInterstital()");
    }

    @Override
    public void bannerAdvert(){
        System.out.println("bannerAdvert()");
    }


    @Override
    public void hideAd(){
        System.out.println("hidebannerAdvert()");
    }

    @Override
    public boolean isSignedIn()
    {
        System.out.println("DesktopGoogleServices: isSignedIn()");
        return false;
    }
}

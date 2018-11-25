package com.mygdx.game.desktop.views;

import com.badlogic.gdx.Game;

import TileMapGame.Play;

public class Box2DTutorial extends Game {
	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private Play mainScreen;
	private EndScreen endScreen;
	private AppPreferences preferences;
	
	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;
	
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		preferences = new AppPreferences();
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}
	
	//Cach thuc thay doi cac cua so
	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
	            this.setScreen(menuScreen);
	            
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if(mainScreen == null) mainScreen = new Play();
				this.setScreen(mainScreen);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
		}
	}

	public AppPreferences getPreferences() {
		return this.preferences;
	}

	public void setPreferences(AppPreferences preferences) {
		this.preferences = preferences;
	}

	
	
//	public static void main(String[] arg) {
//		Box2DTutorial n = new Box2DTutorial();
//		n.create();
//	}
}
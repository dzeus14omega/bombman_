package com.mygdx.game.desktop.views;

import com.badlogic.gdx.Game;

import TileMapGame.Play;
import TileMapGame.Play2;

public class Box2DTutorial extends Game {
	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private Play mainScreen;
	private Play2 mainScreen2;
	private EndScreen endScreen;
	private AppPreferences preferences;
	private LoseScreeen lose;
	private WinScreen win;
	
	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int APPLICATION2 = 6;
	public final static int ENDGAME = 3;
	public final static int LOSESCREEN = 4;
	public final static int WINSCREEN = 5;
	
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
				mainScreen = new Play(this);
				this.setScreen(mainScreen);
				break;
			case APPLICATION2:
				mainScreen2 = new Play2(this);
				this.setScreen(mainScreen2);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
			case LOSESCREEN:
				if(lose == null) lose = new LoseScreeen(this);
				this.setScreen(lose);
				break;
			case WINSCREEN:
				if(win == null) win = new WinScreen(this);
				this.setScreen(win);
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
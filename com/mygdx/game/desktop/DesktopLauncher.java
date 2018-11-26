package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.desktop.views.Box2DTutorial;

public class DesktopLauncher {
	public static void main (String[] arg) {

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "map1";
		cfg.useGL30 = true;
		cfg.width = 960;
		cfg.height = 960;
		
		new LwjglApplication(new Box2DTutorial(), cfg);
	}
}

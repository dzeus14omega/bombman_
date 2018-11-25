package com.mygdx.game.desktop.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen{
	private Box2DTutorial parent;
	private Stage stage;
	private static Music musicStartgame;
	
	class MyActor extends Actor{
		Texture background = new Texture(Gdx.files.internal("skin/bombmanBackGround.png"));
		Texture cuteBackground = new Texture(Gdx.files.internal("skin/cuteBackground.jpg"));
		@Override
		public void draw(Batch batch, float parentAlpha) {
			batch.draw(cuteBackground,0,0);
			batch.draw(background,100,650);
		}
	}
	
	//Constructor
	public MenuScreen(Box2DTutorial box2dTutorial){
		parent = box2dTutorial;
		stage = new Stage(new ScreenViewport());	
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		musicStartgame = Gdx.audio.newMusic(Gdx.files.internal("audio/SuperBomberman-Title.ogg"));
		musicStartgame.setLooping(true); 
		musicStartgame.setVolume(parent.getPreferences().getMusicVolume());
		musicStartgame.play();
		
		Gdx.input.setInputProcessor(stage);
		
		MyActor actor = new MyActor();
		stage.addActor(actor);
		Table table = new Table();
		table.setFillParent(true);
		//table.setDebug(true);
		stage.addActor(table);
		
		//goi den cac skin
		Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		
		//cac Button
		TextButton newGame = new TextButton("New Game", skin);
		TextButton preferences = new TextButton("Preferences", skin);
		TextButton exit = new TextButton("Exit", skin);
		
		//ke bang tao cac nut
		table.add(newGame).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(preferences).fillX().uniformX();
		table.row();
		table.add(exit).fillX().uniformX();
		
		//add event exit,new game, preferences
		exit.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();				
			}
		});
		newGame.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(Box2DTutorial.APPLICATION);	
			}
		});
		preferences.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(Box2DTutorial.PREFERENCES);					
			}
		});
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		musicStartgame.dispose();
		//System.out.println("yes");
	}

	
	//Getter/Setter
	public Box2DTutorial getParent() {
		return parent;
	}
	public void setParent(Box2DTutorial parent) {
		this.parent = parent;
	}

}

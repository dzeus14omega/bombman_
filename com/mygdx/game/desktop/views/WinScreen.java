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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.desktop.views.LoseScreeen.MyActor;

public class WinScreen implements Screen{
	private Box2DTutorial parent;
	private Stage stage;
	private static Music WinMusic;
	
	class MyActor extends Actor{
		Texture background = new Texture(Gdx.files.internal("skin/Victory.png"));
		Texture cuteBackground = new Texture(Gdx.files.internal("skin/cuteBackground.jpg"));
		
		@Override
		public void draw(Batch batch, float parentAlpha) {
			
			batch.draw(cuteBackground,0,0);
			batch.draw(background,180,600,600,300);
		}
	}
	
	//Constructor
		public WinScreen(Box2DTutorial box2dTutorial){
			parent = box2dTutorial;
			stage = new Stage(new ScreenViewport());	
			
		}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

		if(parent.getPreferences().isMusicEnabled()) {
			WinMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/StageCleared.ogg"));
			WinMusic.setVolume(parent.getPreferences().getMusicVolume());
			WinMusic.play();
		}
		
		
		
		Gdx.input.setInputProcessor(stage);
		
		
		MyActor actor = new MyActor();
		stage.addActor(actor);
		Table table = new Table();
		table.setFillParent(true);
		//table.setDebug(true);
		stage.addActor(table);
		
		//Set skin
		Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		TextButton Continue = new TextButton("Continue", skin);
		TextButton MainMenu = new TextButton("Main Menu", skin);
		
		table.add(Continue).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(MainMenu).fillX().uniformX();
		
		Continue.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(Box2DTutorial.APPLICATION2);	
				try {
					WinMusic.dispose();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		MainMenu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(Box2DTutorial.MENU);		
				try {
					WinMusic.dispose();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
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
		WinMusic.dispose();
	}

}

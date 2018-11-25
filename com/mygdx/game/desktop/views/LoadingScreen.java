package com.mygdx.game.desktop.views;

import com.badlogic.gdx.Screen;

public class LoadingScreen implements Screen {
	private Box2DTutorial parent; 

	
	
	
	
	//Constructor
	public LoadingScreen(Box2DTutorial box2dTutorial){
		setParent(box2dTutorial);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		parent.changeScreen(Box2DTutorial.MENU);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		
	}

	
	//Getter/Setter
	public Box2DTutorial getParent() {
		return parent;
	}
	public void setParent(Box2DTutorial parent) {
		this.parent = parent;
	}

}

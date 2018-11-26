package TileMapGame.entities;

import java.util.ArrayList;
import java.util.Stack;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;


import TileMapGame.Play;

public class Bomb extends Sprite{
	private Play parent;
	private int fireLeght =1;
	private float animationTime = 0;
	private float explodeTime = 3;
	private float duration = (float)0.3;
	private boolean bombExist = false;
	private boolean explode=false;
	private TiledMapTileLayer collisionLayer;
	private Animation bombWarn;
	private Stack<Vector2> memFire=new Stack<Vector2>();
	private ArrayList<Vector2> memDeadZone = new ArrayList<Vector2>();
	private Sound bomno;
	//Constructor
	public Bomb(int x, int y, Animation bombWarn, Sprite sprite, TiledMapTileLayer _collisionLayer,Play _parent) {
		// TODO Auto-generated constructor stub
		super(sprite);
		this.setX(x);
		this.setY(y);
		this.bombWarn = bombWarn;
		this.parent = _parent;
		this.setCollisionLayer(_collisionLayer);
		bomno = Gdx.audio.newSound(Gdx.files.internal("sound/Explosion.ogg"));
		
	}
	
	@Override
	public void draw(Batch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		
		
		//Set explode animation
		if(animationTime >= explodeTime && animationTime <  explodeTime + 0.1) {
			if(parent.getParent().getPreferences().isSoundEffectsEnabled()) {
				bomno.play(parent.getParent().getPreferences().getSoundVolume());
				
			}
			
		}
		
		if(animationTime >= explodeTime && animationTime <=explodeTime+duration) {
			
			//---UP
			Vector2 t = new Vector2((int)((getX())/64), (int) ((getY())/64));
			if(!memDeadZone.contains(t)) {
				memDeadZone.add(t);
			}
			for(int i=1;i<=fireLeght;i++) {
				if(i == fireLeght && collisionLayer.getCell((int)((getX())/64), (int) ((getY()+64f*i)/64)) ==null ) {
					spriteBatch.draw(new Texture("entities/fireU.png"),getX(),getY()+64*i,64,64);
					Vector2 tmp = new Vector2((int)((getX())/64), (int) ((getY()+64f*i)/64));
					if(!memDeadZone.contains(tmp)) {
						memDeadZone.add(tmp);
					}
				}
				if(collisionLayer.getCell((int)((getX())/64), (int) ((getY()+64f*i)/64)) ==null ) {
					spriteBatch.draw(new Texture("entities/fireVertical.png"),getX(),getY()+64*i,64,64);
					Vector2 tmp = new Vector2((int)((getX())/64), (int) ((getY()+64f*i)/64));
					if(!memDeadZone.contains(tmp)) {
						memDeadZone.add(tmp);
					}
				}else {
					spriteBatch.draw(new Texture("entities/fireVertical.png"),getX(),getY()+64*i,64,64);
					Vector2 tmp = new Vector2((int)((getX())/64), (int) ((getY()+64f*i)/64));
					memFire.push(tmp);
					break;
				}
			}
			//---Right
			for(int i=1;i<=fireLeght;i++) {
				if(i==fireLeght && collisionLayer.getCell((int)((getX()+64f*i)/64), (int) ((getY())/64)) ==null ) {
					spriteBatch.draw(new Texture("entities/fireR.png"),getX()+64*i,getY(),64,64);
					Vector2 tmp = new Vector2((int)((getX()+64f*i)/64), (int) ((getY())/64));
					if(!memDeadZone.contains(tmp)) {
						memDeadZone.add(tmp);
					}
				}else if(collisionLayer.getCell((int)((getX()+64f*i)/64), (int) ((getY())/64)) ==null ) {
					spriteBatch.draw(new Texture("entities/fireHorizontal.png"),getX()+64*i,getY(),64,64);
					Vector2 tmp = new Vector2((int)((getX()+64f*i)/64), (int) ((getY())/64));
					if(!memDeadZone.contains(tmp)) {
						memDeadZone.add(tmp);
					}
				}else {
					spriteBatch.draw(new Texture("entities/fireHorizontal.png"),getX()+64*i,getY(),64,64);
					Vector2 tmp = new Vector2((int)((getX()+64f*i)/64), (int) ((getY())/64));
					memFire.push(tmp);
					break;
				}
			}
			//---Down
			for(int i=1;i<=fireLeght;i++) {
				if(i== fireLeght && collisionLayer.getCell((int)((getX())/64), (int) ((getY()-64f*i)/64)) ==null ) {
					spriteBatch.draw(new Texture("entities/fireD.png"),getX(),getY()-64*i,64,64);
					Vector2 tmp = new Vector2((int)((getX())/64), (int) ((getY()-64f*i)/64));
					if(!memDeadZone.contains(tmp)) {
						memDeadZone.add(tmp);
					}
				}else if(collisionLayer.getCell((int)((getX())/64), (int) ((getY()-64f*i)/64)) ==null ) {
					spriteBatch.draw(new Texture("entities/fireVertical.png"),getX(),getY()-64*i,64,64);
					Vector2 tmp = new Vector2((int)((getX())/64), (int) ((getY()-64f*i)/64));
					if(!memDeadZone.contains(tmp)) {
						memDeadZone.add(tmp);
					}
				}else {
					spriteBatch.draw(new Texture("entities/fireVertical.png"),getX(),getY()-64*i,64,64);
					Vector2 tmp = new Vector2((int)((getX())/64), (int) ((getY()-64f*i)/64));
					memFire.push(tmp);
					break;
				}
			}
			//---Left
			for(int i=1;i<=fireLeght;i++) {
				if(i == fireLeght && collisionLayer.getCell((int)((getX()-64f*i)/64), (int) ((getY())/64)) ==null) {
					spriteBatch.draw(new Texture("entities/fireL.png"),getX()-64*i,getY(),64,64);
					Vector2 tmp = new Vector2((int)((getX()-64f*i)/64), (int) ((getY())/64));
					if(!memDeadZone.contains(tmp)) {
						memDeadZone.add(tmp);
					}
				} else if(collisionLayer.getCell((int)((getX()-64f*i)/64), (int) ((getY())/64)) ==null ) {
					spriteBatch.draw(new Texture("entities/fireHorizontal.png"),getX()-64*i,getY(),64,64);
					Vector2 tmp = new Vector2((int)((getX()-64f*i)/64), (int) ((getY())/64));
					if(!memDeadZone.contains(tmp)) {
						memDeadZone.add(tmp);
					}
				}else {
					spriteBatch.draw(new Texture("entities/fireHorizontal.png"),getX()-64*i,getY(),64,64);
					Vector2 tmp = new Vector2((int)((getX()-64f*i)/64), (int) ((getY())/64));
					memFire.push(tmp);
					break;
				}
			}
			
			//System.out.println(memDeadZone.size());	
		}
		super.draw(spriteBatch);
	}


	private void update(float deltaTime) {
		// TODO Auto-generated method stub
		bomno=Gdx.audio.newSound(Gdx.files.internal("sound/Explosion.ogg"));
		animationTime += deltaTime;
		if(animationTime>=1) {
			this.bombWarn.setFrameDuration(1/(2f*animationTime));
			
		}
		setRegion((TextureRegion)bombWarn.getKeyFrame(animationTime));
		
		//colision to fire while not explode
		if( !this.explode) {
			
			if( parent.checkDeadzone((int)((getX()+32)/64),(int)((getY()+32)/64)) ) {
				animationTime = explodeTime;
			
			}
			
		}
		
		if(animationTime >= explodeTime && animationTime < explodeTime + duration) {
			this.explode = true;
			this.setTexture(new Texture("entities/fireC.png"));
			parent.setDeadzone(this.memDeadZone, true);
		}
		
		if(animationTime >= explodeTime + duration) {
			while(!memFire.isEmpty()) {
				//System.out.println((int)memFire.peek().x+" "+(int)memFire.peek().y);
				Cell V =collisionLayer.getCell((int)memFire.peek().x, (int)memFire.peek().y);
				if(V!=null){
					//System.out.println(V.getTile().getId());
					if(V.getTile().getId()== 787) {
						
						parent.deleteCell((int)memFire.peek().x,(int)memFire.peek().y);
					}
				}
				memFire.pop();
			}
			parent.setDeadzone(this.memDeadZone, false);
			memDeadZone.clear();

			//Play.deleteCell((int)(getX()/64), (int)(getY()/64));
			
			bombExist = false;
			explode = false;
			setX(2000);
			setY(2000);
			animationTime = 0;
		}
		
		
	}

	
	//Getter/Setter
	public boolean isExplode() {
		return explode;
	}
	public void setExplode(boolean explode) {
		this.explode = explode;
	}
	public int getFireLeght() {
		return fireLeght;
	}
	public void setFireLeght(int fireLeght) {
		this.fireLeght = fireLeght;
	}

	public float getAnimationTime() {
		return animationTime;
	}

	public void setAnimationTime(float animationTime) {
		this.animationTime = animationTime;
	}

	public float getExplodeTime() {
		return explodeTime;
	}

	public void setExplodeTime(float explodeTime) {
		this.explodeTime = explodeTime;
	}

	public boolean isBombExist() {
		return bombExist;
	}

	public void setBombExist(boolean bombset) {
		this.bombExist = bombset;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public ArrayList<Vector2> getMemDeadZone() {
		return memDeadZone;
	}

	public void setMemDeadZone(ArrayList<Vector2> memDeadZone) {
		this.memDeadZone = memDeadZone;
	}

	public Play getParent() {
		return parent;
	}

	public void setParent(Play parent) {
		this.parent = parent;
	}
	
	
	
	
}

package TileMapGame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import TileMapGame.Play;
import TileMapGame.entities.Item.ItemType;

public class Mob extends Sprite {
	private Vector2 velocity = new Vector2();
	private float speed = 10;
	private TiledMapTileLayer collisionLayer;
	private int trangThai; //1: len/ 2: trai/ 3: xuong/ 4: phai
	private boolean life = true;
	private Animation up,down,left,right;
	private float animationTime =0;
	private Player p1;
	
	private Sound SoundDie;
	
	//Constructor
	public Mob(Animation up, Animation down,Animation left, Animation right,Sprite sprite,TiledMapTileLayer _collisionLayer,Player _player) {
		super(sprite);
		this.p1 = _player;
		this.collisionLayer = _collisionLayer;
		this.trangThai = 1;
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.SoundDie = Gdx.audio.newSound(Gdx.files.internal("sound/EnemyDie1.ogg"));
}
	
	@Override
	public void draw(Batch spriteBatch) {

		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}
	
	

	public void update(float delta) {
		float oldX = getX(), oldY = getY();
		boolean collisionX = false, collisionY = false;
		//System.out.println(velocity.x +" "+ velocity.y);
		
		if(velocity.x>0){
			this.setTexture(new Texture("sprite/mobR1.png"));
		}
		if(velocity.x<0){
			this.setTexture(new Texture("sprite/mobL1.png"));
		}
		if(velocity.y>0) {
			this.setTexture(new Texture("sprite/mobU1.png"));
		}
		if(velocity.y<0) {
			this.setTexture(new Texture("sprite/mobD1.png"));
		}
		
		if(this.trangThai == 1) {
			velocity.y = speed;
			velocity.x = 0;
		}
		if(this.trangThai == 2) {
			velocity.x = - speed;
			velocity.y = 0;
		}
		if(this.trangThai == 3) {
			velocity.y = -speed;
			velocity.x = 0;
		}
		if(this.trangThai == 4) {
			velocity.x = speed;
			velocity.y = 0;
		}
		
		
		//move on X
		setX(getX() + velocity.x*delta* 10f);
		
		//---move right
		if(getX() > oldX && this.trangThai == 4) {
			if((getY()+32)/64 - (int)((getY()+32)/64) > 0.35 && (getY()+32)/64 - (int)((getY()+32)/64) <0.65) {
				setY((int)((getY()+32)/64)*64);
			}
			
			if(collisionLayer.getCell((int)((getX()+64f)/64), (int)((getY()+64f-1f)/64)) != null || Play.bombPos((int)((getX()+64f)/64),(int)((getY()+64f-1f)/64)) ) {
				//collisionX = true;
				this.trangThai = 1;
			} else if (collisionLayer.getCell((int)((getX()+64f)/64), (int)((getY()+1f)/64)) != null || Play.bombPos((int)((getX()+64f)/64), (int)((getY()+1f)/64)) ) {
				//collisionX = true;
				this.trangThai = 1;
			}
		}
		//---move left
		if(getX() < oldX && this.trangThai ==2) {
			if((getY()+32)/64 - (int)((getY()+32)/64) > 0.35 && (getY()+32)/64 - (int)((getY()+32)/64) <0.65) {
				setY((int)((getY()+32)/64)*64);
			}
			if(collisionLayer.getCell((int)(getX()/64), (int)((getY()+1f)/64)) != null|| Play.bombPos((int)(getX()/64), (int)((getY()+1f)/64)) ) {
				//collisionX = true;
				this.trangThai = 3;
			} else if (collisionLayer.getCell((int)((getX())/64), (int)((getY()+64f-1f)/64)) != null || Play.bombPos((int)((getX())/64), (int)((getY()+64f-1f)/64))) {
				//collisionX = true;
				this.trangThai = 3;
			}
		}
			
		//move on Y
		setY(getY() + velocity.y*delta*10f);
		//---move up
		if(getY() > oldY && this.trangThai == 1) {
			if((getX()+32)/64 - (int)((getX()+32)/64) > 0.35 && (getX()+32)/64 - (int)((getX()+32)/64) <0.65) {
				setX((int)((getX()+32)/64)*64);
			}
			if(collisionLayer.getCell((int)((getX()+64f-1f)/64), (int)((getY()+64f)/64)) != null || Play.bombPos((int)((getX()+64f-1f)/64), (int)((getY()+64f)/64))) {
				//collisionY = true;
				this.trangThai = 2;
			} else if (collisionLayer.getCell((int)((getX()+1f)/64), (int)((getY()+64f)/64)) != null || Play.bombPos((int)((getX()+1f)/64), (int)((getY()+64f)/64))) {
				//collisionY = true;
				this.trangThai = 2;
			}
		}
		//---move down
		if(getY() < oldY && this.trangThai == 3) {
			if((getX()+32)/64 - (int)((getX()+32)/64) > 0.35 && (getX()+32)/64 - (int)((getX()+32)/64) <0.65) {
				setX((int)((getX()+32)/64)*64);
			}
			if(collisionLayer.getCell((int)((getX()+1f)/64), (int)(getY()/64)) != null || Play.bombPos((int)((getX()+1f)/64), (int)(getY()/64))) {
				//collisionY = true;
				this.trangThai = 4;
			}else if (collisionLayer.getCell((int)((getX()+64f-1f)/64), (int)(getY()/64)) != null || Play.bombPos((int)((getX()+64f-1f)/64), (int)(getY()/64))) {
				//collisionY = true;
				this.trangThai = 4;
			}
		}
		
		//collision to fire
		if( Play.checkDeadzone((int)((getX()+32)/64),(int)((getY()+32)/64)) ) {
			life = false;
			SoundDie.play();
		}
		//collision to player
		if((int)(this.p1.getPosition().x/64) == (int)(getX()/64) && (int)(this.p1.getPosition().y/64) == (int)(getY()/64) ) {
			Player.setLife(false);
		}
		
		//Set animation
		animationTime += delta;
		setRegion((TextureRegion) (velocity.x < 0 ? left.getKeyFrame(animationTime) : velocity.x > 0 ? right.getKeyFrame(animationTime) : velocity.y>0 ? up.getKeyFrame(animationTime) :  down.getKeyFrame(animationTime) ));
	}

	 
	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}


	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public boolean isLife() {
		return life;
	}

	public void setLife(boolean life) {
		this.life = life;
	}

	public float getAnimationTime() {
		return animationTime;
	}

	public void setAnimationTime(float animationTime) {
		this.animationTime = animationTime;
	}

	public Animation getRight() {
		return right;
	}

	public void setRight(Animation right) {
		this.right = right;
	}

	public Player getP1() {
		return p1;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
	}
	
	
	
}

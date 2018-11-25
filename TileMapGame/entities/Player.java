package TileMapGame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

import TileMapGame.Play;

public class Player extends Sprite implements InputProcessor{
	/** the movement velocity */
	enum TrangThai{
		up,
		down,
		left,
		right,
		still,
	}
	
	private TrangThai tt = TrangThai.still;
	private int totalBomb = 1;
	private int fireLength =1;
	private Vector2 velocity = new Vector2();
	private float speed = 15, animationTime=0;
	private TiledMapTileLayer collisionLayer;
	private static boolean life = true;
	private Animation up,down,left,right,still;

	
	
	
	
	//Constructor
	public Player(Animation up, Animation down,Animation left,Animation right,Animation still, Sprite sprite,TiledMapTileLayer _collisionLayer) {
		super(sprite);
		this.collisionLayer = _collisionLayer;
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.still = still;
		/**test*/
		for(int i=0;i<collisionLayer.getWidth();i++) {
			for(int j=0;j<collisionLayer.getHeight();j++) {
				if(collisionLayer.getCell(i, j) == null) {
					System.out.print(" ");
				}else {
					System.out.print("+");
				}
			}
			System.out.print("\n");
		}
		/* make Sound and music */
		
	}
	
	
	
	@Override
	public void draw(Batch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}
	
	
	public void update(float delta) {
		
		//save old pos
		float oldX = getX(), oldY = getY();
		boolean collisionX = false, collisionY = false;
		//System.out.println(getX()/64 + " " + getY()/64);
		
		//set TrangThai character
		if(velocity.x >0) {	
			this.tt = TrangThai.right;
		}
		if(velocity.x<0) {
			this.tt = TrangThai.left;
		}
		if(velocity.y > 0) {
			this.tt = TrangThai.up;
		}
		if(velocity.y<0) {
			this.tt = TrangThai.down;
		}
		
		if(velocity.x==0 && velocity.y == 0) {
			this.tt = TrangThai.still;
		}
		
		
		//move on X
		setX(getX() + velocity.x*delta* 10f);
			
		//---move right
		if(getX() > oldX) {
			if((getY()+32)/64 - (int)((getY()+32)/64) > 0.35 && (getY()+32)/64 - (int)((getY()+32)/64) <0.65) {
				setY((int)((getY()+32)/64)*64);
			}
			
			if(collisionLayer.getCell((int)((getX()+64f)/64), (int)((getY()+64f-1f)/64)) != null || Play.bombPos((int)((getX()+64f)/64),(int)((getY()+64f-1f)/64)) ) {
				collisionX = true;
			} else if (collisionLayer.getCell((int)((getX()+64f)/64), (int)((getY()+1f)/64)) != null || Play.bombPos((int)((getX()+64f)/64), (int)((getY()+1f)/64))) {
				collisionX = true;
			}
		}
		//---move left
		if(getX() < oldX) {
			if((getY()+32)/64 - (int)((getY()+32)/64) > 0.35 && (getY()+32)/64 - (int)((getY()+32)/64) <0.65) {
				setY((int)((getY()+32)/64)*64);
			}
			if(collisionLayer.getCell((int)(getX()/64), (int)((getY()+1f)/64)) != null || Play.bombPos((int)(getX()/64),(int)((getY()+1f)/64)) ) {
				collisionX = true;
			} else if (collisionLayer.getCell((int)((getX())/64), (int)((getY()+64f-1f)/64)) != null|| Play.bombPos((int)((getX())/64), (int)((getY()+64f-1f)/64)) ) {
				collisionX = true;
			}
		}
		//---react to collisionX
		if(collisionX) {
			
			velocity.x = 0;
		}
				
				
		//move on Y
		setY(getY() + velocity.y*delta*10f);
		//---move up
		if(getY() > oldY) {
			if((getX()+32)/64 - (int)((getX()+32)/64) > 0.35 && (getX()+32)/64 - (int)((getX()+32)/64) <0.65) {
				setX((int)((getX()+32)/64)*64);
			}
			if(collisionLayer.getCell((int)((getX()+64f-1f)/64), (int)((getY()+64f)/64)) != null || Play.bombPos((int)((getX()+64f-1f)/64),(int)((getY()+64f)/64)))  {
				collisionY = true;
			} else if (collisionLayer.getCell((int)((getX()+1f)/64), (int)((getY()+64f)/64)) != null || Play.bombPos((int)((getX()+1f)/64), (int)((getY()+64f)/64)) ) {
				collisionY = true;
			}
		}
		//---move down
		if(getY() < oldY) {
			if((getX()+32)/64 - (int)((getX()+32)/64) > 0.35 && (getX()+32)/64 - (int)((getX()+32)/64) <0.65) {
				setX((int)((getX()+32)/64)*64);
			}
			if(collisionLayer.getCell((int)((getX()+1f)/64), (int)(getY()/64)) != null || Play.bombPos((int)((getX()+1f)/64), (int)(getY()/64)) ){
				collisionY = true;
			}else if (collisionLayer.getCell((int)((getX()+64f-1f)/64), (int)(getY()/64)) != null || Play.bombPos((int)((getX()+64f-1f)/64), (int)(getY()/64)) ) {
				collisionY = true;
			}
		}
		
		
		//react to collisionY
		if(collisionY) {
			
			velocity.y = 0;
		}
		
		//collision player to fire
		if( Play.checkDeadzone((int)((getX()+32)/64),(int)((getY()+32)/64)) ) {
			life = false;
		}
//		Sound die = Gdx.audio.newSound(Gdx.files.internal("audio/GameOver.ogg"));
//		if(life==false)
//		{
//			die.play();
//			music.dispose();
//		}
		// update animation
		animationTime += delta;
		setRegion((TextureRegion) (velocity.x < 0 ? left.getKeyFrame(animationTime) : velocity.x > 0 ? right.getKeyFrame(animationTime) : velocity.y>0 ? up.getKeyFrame(animationTime) : velocity.y<0 ? down.getKeyFrame(animationTime) : still.getKeyFrame(animationTime)));
		
	}

	
	
	
	
	
	/**Controller */
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		
		switch (keycode) {
		case Keys.W:
		case Keys.UP:
			velocity.y = speed;
			velocity.x = 0;
			animationTime = 0;
			//this.setTexture(new Texture("sprite/MHdown.png"));
			break;
		case Keys.S:
		case Keys.DOWN:
			velocity.y = -speed;
			velocity.x = 0;
			animationTime = 0;
			//this.setTexture(new Texture("sprite/myhero.png"));
			break;
		case Keys.A:
		case Keys.LEFT:
			velocity.x = -speed;
			velocity.y = 0;
			animationTime = 0;
			//this.setTexture(new Texture("sprite/MHleft.png"));
			break;
		case Keys.D:
		case Keys.RIGHT:
			velocity.x = speed;
			velocity.y = 0;
			animationTime = 0;
			//this.setTexture(new Texture("sprite/MHright.png"));
			break;
		case Keys.SPACE:
			//Bomb Bomb = new Bomb((int)(getX()),(int)(getY()));
			if(life==true) {
				Play.Setbomb((int)getX(),(int)getY(), totalBomb, fireLength);
//				if(this.tt == TrangThai.still) {
//					Play.Setbomb((int)getX(),(int)getY(), totalBomb, fireLength);
//				}
//				if(this.tt == TrangThai.up) {
//					Play.Setbomb((int)getX(),(int)getY()-32, totalBomb, fireLength);
//				}
//				if(this.tt == TrangThai.down) {
//					Play.Setbomb((int)getX(),(int)getY()+32, totalBomb, fireLength);
//				}
//				if(this.tt == TrangThai.left) {
//					Play.Setbomb((int)getX()+32,(int)getY(), totalBomb, fireLength);
//				}
//				if(this.tt == TrangThai.right) {
//					Play.Setbomb((int)getX()-32,(int)getY(), totalBomb, fireLength);
//				}
			}
			break;
		}
		return true;
	}

	
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		switch (keycode) {
		case Keys.A:	
		case Keys.D:
			velocity.x = 0;
			animationTime = 0;
			break;
		case Keys.W:
		case Keys.S:
			velocity.y = 0;
			animationTime = 0;
			break;
		}
		return true;
		
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	//Getter/setter
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

 
	public static boolean isLife() {
//		if(life == false) {
//			Sound die = Gdx.audio.newSound(Gdx.files.internal("audio/GameOver.ogg"));
//			die.play();
//			music.dispose();
//		}
		return life;
	}


	public static void setLife(boolean _life) {
		life = _life;
	}


	public Animation getRight() {
		return right;
	}


	public void setRight(Animation right) {
		this.right = right;
	}
	
	public Animation getLeft() {
		return left;
	}
	
	public void setTotalBomb(int n) {
		this.totalBomb = n;
	}
	public int getTotalBomb() {
		return totalBomb;
	}
	
	public Vector2 getPosition() {
		Vector2 tmp = new Vector2(getX(), getY());
		return tmp;
	}



	public int getFireLength() {
		return fireLength;
	}



	public void setFireLength(int fireLength) {
		this.fireLength = fireLength;
	}



	public TrangThai getTt() {
		return tt;
	}



	public void setTt(TrangThai tt) {
		this.tt = tt;
	}
}





/**
public void update(float delta) {
	//extra addading gravity
	velocity.y -= gravity * delta;
	
	//clamp velocity
	if(velocity.y > speed) {
		velocity.y = speed;
	} else if(velocity.y < -speed) {
		velocity.y = -speed;
	}
	
	//save old pos
	float oldX = getX(), oldY = getY();
	boolean collisionX = false, collisionY = false;
	System.out.println((int)getX() + " " + (int)getY());
	
	//set texture for sprite
	if(velocity.x >0) {
		this.setTexture(new Texture("sprite/MHright.png"));
	}
	if(velocity.x<0) {
		this.setTexture(new Texture("sprite/MHleft.png"));
	}
	if(velocity.y > 0) {
		this.setTexture(new Texture("sprite/MHdown.png"));
	}
	if(velocity.y<0) {
		this.setTexture(new Texture("sprite/myhero.png"));
	}
	
	//move on X
	setX(getX() + velocity.x*delta* 1f);
			
	//set value of collisionX
	if(velocity.x <0) {
		// top left
		collisionX = isCellBlocked(getX(), getY() + getHeight());
		//middle left
		if(!collisionX) {
			collisionX = isCellBlocked(getX(), (getY() + getHeight())/2);
		}
		//
		if(!collisionX) {
			collisionX = isCellBlocked(getX(),getY());
		}
	}else if(velocity.x >0){
		//top right
		collisionX = isCellBlocked(getX() + getWidth(), getY() + getHeight());
		//middle right
		if(!collisionX) {
			collisionX = isCellBlocked(getX() + getWidth(), (getY() + getHeight())/2);
		}
		//bottom right
		if(!collisionX) {
			collisionX = isCellBlocked(getX() + getWidth(), getY());
		}
	}
	
	//react to collisionX
	if(collisionX) {
		setX(oldX);
		velocity.x = 0;
	}
	
	//move on Y
	setY(getY() + velocity.y*delta*1f);
			
	if(velocity.y <0) {
		//bottom left
		collisionY = isCellBlocked(getX(), getY());
		//bottom
		if(!collisionY) {
			collisionY = isCellBlocked((getX()+getWidth())/2, getY());
		}
		//bottom right
		if(!collisionY) {
			collisionY = isCellBlocked(getX()+getWidth(), getY());
		}
	}else if(velocity.y >0){
		//left top
		collisionY = isCellBlocked(getX(), getY() + getHeight());
		//top
		if(!collisionY) {
			collisionY = isCellBlocked((getX()+getWidth())/2, getY() + getHeight());
		}
		//right top
		if(!collisionY) {
			collisionY = isCellBlocked(getX()+getWidth(), getY() + getHeight());
		}
	}
	
	//react to collisionY
	if(collisionY) {
		setY(oldY);
		velocity.y = 0;
	}

	
	
}

//	private boolean isCellBlocked(float x,float y) {
//		
//		Cell cell = collisionLayer.getCell((int)(x / collisionLayer.getTileWidth()),(int)(y / collisionLayer.getTileHeight()));
//		return cell != null && cell.getTile() != null && (cell.getTile().getProperties().containsKey("block") || cell.getTile().getProperties().containsKey("hamblock"));
//
//	}
//
*/
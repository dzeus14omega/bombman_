package TileMapGame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import TileMapGame.Play;


public class Item extends Sprite {
	public enum ItemType{
		boost,
		fire,
		extraBomb,
		finish
	}
	private ItemType itemType;
	private Player p1;
	private boolean appear = false;
	private TiledMapTileLayer posInMap;
	private Play parent;
	
	//Constructor
	public Item(ItemType a,Sprite sprite, TiledMapTileLayer _posInMap, Player _player1,Play _play) {
		super(sprite);
		this.parent = _play;
		setItemType(a);
		this.setP1(_player1);
		this.posInMap = _posInMap;
	}
	
	@Override
	public void draw(Batch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}
	
	
	private void update(float deltaTime) {
		// TODO Auto-generated method stub
		//System.out.println((int)(getX()/64) +" "+(int)(getY()/64) );
		if(posInMap.getCell((int)(getX()/64), (int)(getY()/64)) == null ) {
			this.appear = true;
		}
		
		
			
		if((int)(this.p1.getPosition().x/64) == (int)(getX()/64) && (int)(this.p1.getPosition().y/64) == (int)(getY()/64) ) {
			if(itemType == ItemType.extraBomb) {
				this.p1.setTotalBomb(p1.getTotalBomb() + 1);
			}
			
			if(itemType == ItemType.fire) {
				this.p1.setFireLength(p1.getFireLength()+1);
			}
			
			if(itemType == ItemType.boost) {
				this.p1.setSpeed(p1.getSpeed()+5);
			}
			this.appear = false;
			if(itemType == ItemType.finish) {
				parent.setWinCondition(true);
				this.appear = true;
			}
		}
		
		
	}

	
	
	//Getter/Setter
	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public Player getP1() {
		return p1;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
	}

	public boolean isAppear() {
		return appear;
	}

	public void setAppear(boolean appear) {
		this.appear = appear;
	}
}

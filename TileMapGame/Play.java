package TileMapGame;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.mygdx.game.desktop.views.Box2DTutorial;

import TileMapGame.entities.Bomb;
import TileMapGame.entities.Item;
import TileMapGame.entities.Item.ItemType;
import TileMapGame.entities.Mob;
import TileMapGame.entities.Player;


public class Play implements Screen {
	private Box2DTutorial parent;
	private static TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Player player;
	private static Music music;
	
	
	private static Item[] item;
	private static Bomb[] bomb;
	private static Mob[] mob;
	private static boolean[][] deadzone;

	@Override
	public void show() {
		// TODO Auto-generated method stub
		map = new TmxMapLoader().load("maps/Dz.tmx");

		renderer = new OrthogonalTiledMapRenderer(map,2);
		camera = new OrthographicCamera();
		camera.translate(480, 480);
		
		/*set dead zone*/
		deadzone = new boolean[15][15];
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				deadzone[i][j] = false;
			}
		}
		
		//Set music
		if(parent.getPreferences().isMusicEnabled()) {
			if(player.isLife()) {
				music = Gdx.audio.newMusic(Gdx.files.internal("audio/SuperBomberman-Area1.ogg"));
				music.setLooping(true); 
				music.setVolume(parent.getPreferences().getMusicVolume());
				music.play();
			}
		}
		
		
		
		/**Player and Player's animation*/
		Animation MHup = new Animation(1/4f,new TextureRegion(new Texture("sprite/MHdown.png")), new TextureRegion(new Texture("sprite/MHdown3.png")) );
		MHup.setPlayMode(PlayMode.LOOP);
		Animation MHdown =  new Animation(1/4f,new TextureRegion(new Texture("sprite/myhero.png")), new TextureRegion(new Texture("sprite/myhero3.png")) );
		MHdown.setPlayMode(PlayMode.LOOP);
		Animation MHleft = new Animation(1/4f,new TextureRegion(new Texture("sprite/MHleft.png")), new TextureRegion(new Texture("sprite/MHleft3.png")) );
		MHleft.setPlayMode(PlayMode.LOOP);
		Animation MHright = new Animation(1/4f,new TextureRegion(new Texture("sprite/MHright.png")), new TextureRegion(new Texture("sprite/MHright3.png")) );
		MHright.setPlayMode(PlayMode.LOOP);
		Animation MHstill = new Animation(1f,new TextureRegion(new Texture("sprite/myhero2.png")));
		MHstill.setPlayMode(PlayMode.LOOP);
		player = new Player(MHup,MHdown,MHleft,MHright,MHstill,new Sprite(new Texture("sprite/myhero.png")),(TiledMapTileLayer)map.getLayers().get("object"));
		player.setPosition(4*2* player.getCollisionLayer().getTileWidth(), 1*2*player.getCollisionLayer().getTileHeight());
		player.setSize(player.getWidth()*2, player.getHeight()*2);		//dat lai size nhan vat
		
		/**Bomb and Bomb's animation*/
		Animation bombWarn = new Animation(1/2f, new TextureRegion(new Texture("entities/bomb1.png")),new TextureRegion(new Texture("entities/bomb2.png")));
		bombWarn.setPlayMode(PlayMode.LOOP);
		bomb = new Bomb[8];
		for(int i=0;i<8;i++) {
			bomb[i] = new Bomb(0, 0,bombWarn, new Sprite(new Texture("entities/bomb1.png")),(TiledMapTileLayer)map.getLayers().get("object"));
			bomb[i].setSize(bomb[i].getWidth()*2, bomb[i].getHeight()*2);
			
		}
		
		/**Mob and Mob's animation*/
		Animation up = new Animation(1/6f,new TextureRegion(new Texture("sprite/mobU2.png")), new TextureRegion(new Texture("sprite/mobU3.png")) );
		up.setPlayMode(PlayMode.LOOP);
		Animation down =  new Animation(1/6f,new TextureRegion(new Texture("sprite/mobD1.png")), new TextureRegion(new Texture("sprite/mobD2.png")) );
		down.setPlayMode(PlayMode.LOOP);
		Animation left = new Animation(1/6f,new TextureRegion(new Texture("sprite/mobL1.png")), new TextureRegion(new Texture("sprite/mobL3.png")) );
		left.setPlayMode(PlayMode.LOOP);
		Animation right = new Animation(1/6f,new TextureRegion(new Texture("sprite/mobR1.png")), new TextureRegion(new Texture("sprite/mobR3.png")) );
		right.setPlayMode(PlayMode.LOOP);
		
		Mob mob1 = new Mob(up,down,left,right,new Sprite(new Texture("sprite/mobD1.png")), (TiledMapTileLayer)map.getLayers().get("object"),player);
		mob1.setPosition(4*2*mob1.getCollisionLayer().getTileWidth(), 5*2*mob1.getCollisionLayer().getTileHeight());
		mob1.setSize(mob1.getWidth()*2, mob1.getHeight()*2);
		
		Mob mob2 = new Mob(up,down,left,right,new Sprite(new Texture("sprite/mobD1.png")), (TiledMapTileLayer)map.getLayers().get("object"),player);
		mob2.setPosition(9*2*mob2.getCollisionLayer().getTileWidth(), 7*2*mob2.getCollisionLayer().getTileHeight());
		mob2.setSize(mob2.getWidth()*2, mob2.getHeight()*2);
		
		Mob mob3 = new Mob(up,down,left,right,new Sprite(new Texture("sprite/mobD1.png")), (TiledMapTileLayer)map.getLayers().get("object"),player);
		mob3.setPosition(2*2*mob3.getCollisionLayer().getTileWidth(), 13*2*mob3.getCollisionLayer().getTileHeight());
		mob3.setSize(mob3.getWidth()*2, mob3.getHeight()*2);
		
		Mob mob4 = new Mob(up,down,left,right,new Sprite(new Texture("sprite/mobD1.png")), (TiledMapTileLayer)map.getLayers().get("object"),player);
		mob4.setPosition(13*2*mob4.getCollisionLayer().getTileWidth(), 13*2*mob4.getCollisionLayer().getTileHeight());
		mob4.setSize(mob4.getWidth()*2, mob4.getHeight()*2);
		
		mob = new Mob[]{mob1,mob2,mob3,mob4};
		
		
		//Set Item
		Item extraBomb1 =new Item(ItemType.extraBomb, new Sprite(new Texture("entities/extraBombIcon.png")),  (TiledMapTileLayer)map.getLayers().get("object"), player);
		extraBomb1.setPosition(1*2*32, 3*2*32);
		extraBomb1.setSize(extraBomb1.getWidth()*2, extraBomb1.getHeight()*2);
		
		Item boost = new Item(ItemType.boost, new Sprite(new Texture("entities/Boost.png")), (TiledMapTileLayer)map.getLayers().get("object"), player);
		boost.setPosition(10*2*32, 4*2*32);
		boost.setSize(boost.getWidth()*2, boost.getHeight()*2);
		
		Item extraFire = new Item(ItemType.fire, new Sprite(new Texture("entities/extraFireIcon.png")), (TiledMapTileLayer)map.getLayers().get("object"), player);
		extraFire.setPosition(4*2*32, 9*2*32);
		extraFire.setSize(extraFire.getWidth()*2, extraFire.getHeight()*2);
		
		item = new Item[] {extraBomb1,boost,extraFire};
		
		
		Gdx.input.setInputProcessor(player);
		
	}

	//Bomb set/ max 8 bomb
	public static void Setbomb(int x,int y,int n, int fireLength) {
		int count =0;
		int mem = 0;
		for(int i=0;i<8;i++) {
			if(bomb[i].isBombExist()) {
				count++;
			}
			if(count >= n) {
				return;
			}
			if(!bomb[i].isBombExist()) {
				mem =i;
			}
		}
		bomb[mem].setFireLeght(fireLength);
		bomb[mem].setX((int)((x+32)/64)*64);
		bomb[mem].setY((int)((y+32)/64)*64);
		bomb[mem].setBombExist(true);
		//addTmpCell(x, y);
	}
	
	public static void deleteCell(int x,int y) {
		((TiledMapTileLayer) map.getLayers().get("object")).setCell(x, y, null);
		for(int i=0;i<item.length;i++) {
			if((int)(item[i].getX()/64) == x && (int)(item[i].getY()/64) == y) {
				item[i].setAppear(true);
			}
		}
	}
//	public static void addTmpCell(int x, int y) {
//		((TiledMapTileLayer) map.getLayers().get("object")).setCell((int)((x+32)/64), (int)((y+32)/64),((TiledMapTileLayer) map.getLayers().get("map")).getCell(0,0) );
//	}
	
//	public static boolean isCellNull(int x,int y) {
//		return ((TiledMapTileLayer) map.getLayers().get("object")).getCell(x, y) == null ? true : false;
//	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// set cam doi theo nhan vat
		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		camera.update();
	
		renderer.setView(camera);
		renderer.render();
		
		renderer.getBatch().begin();
		for(int i=0;i<8;i++) {
			if(bomb[i].isBombExist()) {
				bomb[i].draw(renderer.getBatch());
			}
		}
		
		for(int i=0;i<item.length;i++) {
			if(item[i].isAppear()) {
				item[i].draw(renderer.getBatch());
			}
		}
		
		if(player.isLife()==true) {
			player.draw(renderer.getBatch());
		}
		
		for(int i=0;i<mob.length;i++) {
			if(mob[i].isLife()) {
				mob[i].draw(renderer.getBatch());
			}
		}
		renderer.getBatch().end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
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
		dispose();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		map.dispose();
		renderer.dispose();
		player.getTexture().dispose();
		for(int i=0;i<mob.length;i++) {
			mob[i].getTexture().dispose();
		}
	
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
//	public static Mob getMob1() {
//		return mob1;
//	}

	public static boolean[][] getDeadzone() {
		return deadzone;
	}

	public static void setDeadzone(ArrayList <Vector2> _deadzone,boolean t) {
		for(int i=0;i< _deadzone.size();i++ ) {
			deadzone[(int)_deadzone.get(i).x][(int)_deadzone.get(i).y] = t;
		}
	}

	public static boolean checkDeadzone(int x,int y) {
		return deadzone[x][y];
	}

	public static boolean bombPos(int x, int y) {
		// TODO Auto-generated method stub
		for(int i=0;i<8;i++) {
			if((int)((bomb[i].getX()+32)/64) == x && (int)((bomb[i].getY()+32)/64)==y) {
				return true;
			}
		}
		
		return false;
	}

	
}





/**for test map // on contructor*/
//TiledMapTileLayer collisionLayer = (TiledMapTileLayer)map.getLayers().get("map");
//this.collisionLayer = _collisionLayer;
//for(int i=0;i<collisionLayer.getWidth();i++) {
//	for(int j=0;j<collisionLayer.getHeight();j++) {
//		if(collisionLayer.getCell(i, j) == null) {
//			System.out.print(" ");
//		}else {
//			System.out.print("+");
//		}
//	}
//	System.out.print("\n");
//}



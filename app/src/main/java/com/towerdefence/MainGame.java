package com.towerdefence;

import java.io.IOException;
import java.util.ArrayList;

import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Display;
import org.anddev.andengine.engine.camera.Camera;


import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.entity.sprite.Sprite;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;


import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.input.touch.TouchEvent;

public class MainGame extends BaseGameActivity  implements IOnSceneTouchListener {
	//Global Variables
	
	BitmapTextureAtlas mBitmapTextureAtlas;
	
	TextureRegion backgroundTexture;
	TextureRegion cannonTexture;
	TiledTextureRegion enemyTexture;
	TiledTextureRegion buttonTexture;
	
	Sprite background;

    public static final int GAME_LOST = 1;
    public static final int GAME_WON = 2;

	TimerHandler gameTimer;
	float timerDelay = 0.01f;
	
	public static Sound shootingSound;
	
	public static TextureRegion bulletTexture;
    public static TiledTextureRegion explosionTexture;
    public static TiledTextureRegion freeTexture;
	
	public static ArrayList<Cannon> cannonList;
	public static ArrayList<Bullet> bulletList;
	public static ArrayList<Enemy> enemyList;
    public static ArrayList<Explosion> explosionList;
    public static ArrayList<FreePlace> freePlaceList;
	
	public static Scene MainScene; //Main Game Scene 
	
	
	int numEnemies;
	int spawnCounter;
	int spawnDelay;
	int enemiesSpawn;
	int waveNum;
	
	public static int money;
	public static ChangeableText moneyLabel;
	Font mFont;
	BitmapTextureAtlas mFontTexture;
	Button towerButton1;
	//Button towerButton1;
	
	
	Camera mCamera; //the game Camera
	Display display; //to get all the properties for the current device
	int cameraWidth; //Store Camera Width
	int cameraHeight; // Store Camera Height
	
	
	@Override
	public void onLoadComplete() {
	}

	@Override
	public Engine onLoadEngine() {
		// getting the device's screen size
		display = getWindowManager().getDefaultDisplay();

        cameraWidth = 1024;
        cameraHeight = 624;

		// setting up the camera for the Engine
		mCamera = new Camera(0, 0, cameraWidth, cameraHeight);

		// load into the Engine
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE,
				new RatioResolutionPolicy(cameraWidth, cameraHeight), mCamera)
				.setNeedsSound(true).setNeedsMusic(true));
	}

	@Override
	public void onLoadResources() {
		mBitmapTextureAtlas = new BitmapTextureAtlas(4096, 4096, TextureOptions.BILINEAR_PREMULTIPLYALPHA); //Background size
		
		try {
		    shootingSound = SoundFactory.createSoundFromAsset(mEngine
		        .getSoundManager(), this, "Bullet.mp3");
		} catch (IllegalStateException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}


        //backgroundTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "Grassybackground.png", 600, 600); //Background image
        backgroundTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "background.png", 624, 624); //Background image
        cannonTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "delo2.png",0, 128);
		enemyTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "catenemy.png", 0, 228, 5, 1);
		bulletTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "Bullet.png",128, 0);
		buttonTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "buyButton.png", 128, 128, 2 , 1);
        explosionTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "Explosion.png", 2000, 2000, 6, 1); // x, y, number of cells, number of rows
        freeTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "free.png", 2500, 2500, 6, 1); // x, y, number of cells, number of rows

        mEngine.getTextureManager().loadTexture(mBitmapTextureAtlas);	// loading textures in the engine
		
		mFontTexture = new BitmapTextureAtlas(256, 256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mFont = new Font(mFontTexture, Typeface.create(Typeface.DEFAULT,Typeface.BOLD), 60, true, Color.WHITE);
		mEngine.getTextureManager().loadTexture(mFontTexture);
		mEngine.getFontManager().loadFont(mFont);
	}

	@Override
	public Scene onLoadScene() {
		cannonList = new ArrayList<Cannon>();
		bulletList = new ArrayList<Bullet>();
		enemyList = new ArrayList<Enemy>();
		explosionList = new ArrayList<Explosion>();
        freePlaceList = new ArrayList<FreePlace>();

        //numEnemies = 6;
        numEnemies = 10;
        spawnCounter = 0;
		spawnDelay = 400;
		enemiesSpawn = 0;
		waveNum = 1;
		money = 50;
		
		MainScene = new Scene(); //initialize
		MainScene.setOnSceneTouchListener(this); //Attaches the Screen listener onto the Main Screen
		
		background = new Sprite(0, 0, backgroundTexture);
		MainScene.attachChild(background, 0);
		
		moneyLabel = new ChangeableText(20, 16, mFont, money + " $"); //The label trims the max digits that can be display, adding spaces will allow us to display more characters
		MainScene.attachChild(moneyLabel);
		
		towerButton1 = new Button(920, 25, buttonTexture.deepCopy());
		MainScene.attachChild(towerButton1);
		

		timer();
		return MainScene; // Loading the Main Game Scene
	}

	private void timer() {
		
		gameTimer = new TimerHandler(timerDelay, true, new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						//Code to call in the Timer
						spawnEnemies();
						
						updateSprites();
						removeSprites();
					}
				});
		getEngine().registerUpdateHandler(gameTimer);
	}
	
	private void updateSprites(){
		
		for(int i = 0; i < cannonList.size(); i++){
			for(int j = 0; j < enemyList.size(); j++){
				
				float enemyDistance = getDistance(enemyList.get(j), cannonList.get(i));
				if (enemyList.get(j).active && enemyDistance < 200){
					
					
					cannonList.get(i).fire(j);

					float a = getAngle(enemyList.get(j), cannonList.get(i));
					if (enemyList.get(j).getX() > cannonList.get(i).getX()) {
						cannonList.get(i).setRotation(
								(float) Math.toDegrees(a) + 450);
					} else {
						cannonList.get(i).setRotation(
								-(float) Math.toDegrees(a) - 90);
					}

					break; // Stops the Loops, if not the tower will fire
							// multiple shots at once
				}		
			}
		}
		
		for(int i = 0; i < enemyList.size(); i++){
			if (enemyList.get(i).active){
                enemyList.get(i).move(0.4f);
			}
		}
		
		
		
		
		
		for (int i = 0; i < bulletList.size(); i++){
			if (bulletList.get(i).active){
				int target = bulletList.get(i).target;
				//bulletList.get(i).angle = getAngle(enemy, bulletList.get(i) );
				bulletList.get(i).angle = getAngle(enemyList.get(target), bulletList.get(i) );
				

				if (enemyList.get(target).location.x > this.bulletList.get(i).getX()){
					bulletList.get(i).move(1);
				}else{
					bulletList.get(i).move(-1);
				}
				
				
				if(bulletList.get(i).collidesWith(enemyList.get(target))){
					bulletList.get(i).hit();
					enemyList.get(target).takeDamage(10);
					Explosion a = new Explosion(bulletList.get(i).getX(), bulletList.get(i).getY(), explosionTexture.deepCopy());
				}
			}
		}
	}
	
	private void removeSprites(){
		for (int i = 0; i < enemyList.size(); i++){
			if (enemyList.get(i).getX() < 20){
				enemyList.get(i).active = false;
				MainScene.detachChild(enemyList.get(i));

                getEngine().stop();
                getEngine().unregisterUpdateHandler(gameTimer);

                setResult(GAME_LOST);
                finish();
			}
		}

        if (!enemyList.isEmpty()) {

            boolean stillAlive = false;
            for (Enemy enemy : enemyList) {
                stillAlive |= enemy.isAlive();
            }

            if (!stillAlive) {
                getEngine().stop();
                getEngine().unregisterUpdateHandler(gameTimer);

                setResult(GAME_WON);
                finish();
            }
        }
		
		for (int i = 0; i < explosionList.size(); i ++){
			if (explosionList.get(i).getCurrentTileIndex() == 5){
				MainScene.detachChild(explosionList.get(i));
			}
		}

        if (!towerButton1.pressed) {
            for (FreePlace freePlace : freePlaceList) {
                MainScene.detachChild(freePlace);
            }
            freePlaceList.clear();
        }
	}
	
	//MainScene.detachChild(enemyList.get(0));

	private float getDistance(Enemy enemy, Sprite s2){
		float x = enemy.getX() - s2.getX();
		float y = enemy.getY() - s2.getY();
		float h = (float) Math.sqrt((x*x) + (y*y));
		
		return h;
	}
	
	private float getAngle(Enemy enemy, Sprite s2){
		float y = enemy.getY() - s2.getY();
		return (float) Math.asin( y/getDistance(enemy,s2) );	
	}
	
	private void spawnEnemies() {
		if (spawnCounter > spawnDelay && enemiesSpawn < numEnemies) {
			Enemy e = new Enemy(1000, 310, enemyTexture.deepCopy()); // .deepCopy() stops the sprites from animating at the same time
            //Enemy e = new Enemy(0, 310, enemyTexture.deepCopy());
			e.animate(300); // how Fast to Anime the Larger the number the slower
			MainScene.attachChild(e);
			spawnCounter = 0;
			enemiesSpawn++;
		}
		spawnCounter++;
	}
	
	@Override
		public boolean onSceneTouchEvent(Scene arg0, TouchEvent pSceneTouchEvent) {
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			     float touchX = pSceneTouchEvent.getX();
			     float touchY = pSceneTouchEvent.getY();

                buyCannon(touchX, touchY);
			     	
			        return true;
			    }
			       return false;
		}
	
	public void buyCannon(float touchX, float touchY){
		if (towerButton1.pressed == true) {
			gridBuild(touchX, touchY);

            towerButton1.unpress();

		} else {
			if ((towerButton1.contains(touchX, touchY)) && money - 10 >= 0) {
				towerButton1.press();
				money = money - 10;
				updateMoney(money);
                for (int y = 100; y < 600; y+=100) {
                    for (int x = 60; x < 1000; x += 106) {
                        if (!buildArea(x,y)) {
                            new FreePlace(x + 15, y + 15, freeTexture.deepCopy());
                        }
                    }
                }
			}
		}

	}
	
		private void gridBuild(float touchX, float touchY){
			int x = (int) touchX;
			int y = (int) touchY;

            if ( ((x - 63) % 212) < 106 ) {
                x = ((x - 63) / 212) * 212 + 60;
            } else {
                x = ((x - 63) / 212) * 212 + 166;
            }

            if ( ((y-7) % 200) < 100 ) {
                y = ((y - 7) / 200) * 200;
            } else {
                y = ((y - 7) / 200) * 200 + 100;
            }

            Log.i("MainGame","x:" + x + " y:"+ y);

            if (buildArea(x, y) ){
				money = money + 10;
                updateMoney(money);
			}else {
                Cannon cannon = new Cannon(x, y, cannonTexture);////
                MainScene.attachChild(cannon);
            }
		}
		
		private boolean buildArea(int x, int y){

            if (x ==  60 && y == 300) {return true;}
            if (x == 166 && y == 300) {return true;}
            if (x == 272 && y == 300) {return true;}
            if (x == 272 && y == 400) {return true;}
            if (x == 272 && y == 500) {return true;}
            if (x == 378 && y == 500) {return true;}
            if (x == 484 && y == 500) {return true;}
            if (x == 484 && y == 400) {return true;}
            if (x == 484 && y == 300) {return true;}
            if (x == 484 && y == 200) {return true;}
            if (x == 484 && y == 100) {return true;}
            if (x == 590 && y == 100) {return true;}
            if (x == 696 && y == 100) {return true;}
            if (x == 696 && y == 200) {return true;}
            if (x == 696 && y == 300) {return true;}
            if (x == 802 && y == 300) {return true;}
            if (x == 908 && y == 300) {return true;}

			//Also checks if there is a tower there.
			for (int i = 0; i < cannonList.size(); i++){
				int checkX = (int) cannonList.get(i).getX();
				int checkY = (int) cannonList.get(i).getY();
				
				if (checkX == x && checkY == y){
					return true;
				}
			}

			
			if (y < 100) { // if it is on the dark strip
                return true;
            }
			return false;
		}
    
        public static void updateMoney(int m) {
            money = m;
            moneyLabel.setText(m + " $");
        }

        public static void updateMoney() {
            moneyLabel.setText(money + " $");
        }

		
	}

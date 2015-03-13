





package com.towerdefence.gameplay;

import com.towerdefence.MainGame;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class Cannon extends Sprite {
	int fireDelay = 50;
	int counter = 0;
	
	public Cannon(float pX, float pY, TextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion);
		
		MainGame.cannonList.add(this);
	}
	
	public void fire(int target){
		
		if (counter > fireDelay){
			MainGame.shootingSound.play(); 
			Bullet b = new Bullet(target, 5f, this.getX() + this.getWidth()/2, this.getY() + this.getHeight()/2, MainGame.bulletTexture);
			MainGame.MainScene.attachChild(b);
			counter = 0;
		}
		counter = counter + 1;
	}

	

}













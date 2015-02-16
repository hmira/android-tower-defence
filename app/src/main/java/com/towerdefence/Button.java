package com.towerdefence;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Button extends AnimatedSprite{

	public boolean pressed;
	
	public Button(float pX, float pY, TiledTextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion);
		
	}
	
	public void press(){
	this.setCurrentTileIndex(1);
		pressed = true;
	}
	
	public void unpress(){
		this.setCurrentTileIndex(0);
		this.pressed = false;
	}

}

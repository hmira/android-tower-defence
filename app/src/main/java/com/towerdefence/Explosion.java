package com.towerdefence;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Explosion extends AnimatedSprite {

	public Explosion(float x, float y, TiledTextureRegion pTextureRegion) {
		super(x, y, pTextureRegion);
		// TODO Auto-generated constructor stub
		this.animate(60);
		MainGame.explosionList.add(this);
		MainGame.MainScene.attachChild(this);
	}
}

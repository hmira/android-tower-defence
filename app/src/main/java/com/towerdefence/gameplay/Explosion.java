package com.towerdefence.gameplay;

import com.towerdefence.MainGame;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Explosion extends AnimatedSprite {

    public Explosion(float x, float y, TiledTextureRegion pTextureRegion) {
        super(x, y, pTextureRegion);
        this.animate(60);
        MainGame.explosionList.add(this);
        MainGame.MainScene.attachChild(this);
    }
}

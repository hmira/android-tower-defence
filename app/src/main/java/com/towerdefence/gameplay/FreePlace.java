package com.towerdefence.gameplay;

import com.towerdefence.MainGame;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class FreePlace extends AnimatedSprite {

    public FreePlace(float x, float y, TiledTextureRegion pTextureRegion) {
        super(x, y, pTextureRegion);
        this.animate(60);
        MainGame.freePlaceList.add(this);
        MainGame.MainScene.attachChild(this);
    }
}

package com.towerdefence;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

/**
 * Created by Peter on 2/15/2015.
 */
public class FreePlace extends AnimatedSprite {

    public FreePlace(float x, float y, TiledTextureRegion pTextureRegion) {
        super(x, y, pTextureRegion);
        // TODO Auto-generated constructor stub
        this.animate(60);
        MainGame.freePlaceList.add(this);
        MainGame.MainScene.attachChild(this);
    }
}

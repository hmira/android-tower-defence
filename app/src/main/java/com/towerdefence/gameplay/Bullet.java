package com.towerdefence.gameplay;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.graphics.PointF;

import com.towerdefence.MainGame;

public class Bullet extends Sprite {

    public boolean active;
    public float angle;
    private float bulletSpeed;
    private PointF location;
    //Chapter 7
    public int target;


    public Bullet(int t, float b, float pX, float pY, TextureRegion pTextureRegion) {
        super(pX, pY, pTextureRegion);
        this.active = true;
        this.target = t;
        this.bulletSpeed = b;
        this.location = new PointF(pX, pY);
        MainGame.bulletList.add(this);
    }

    public void move(int direction) {
        //targetEnemy(); getSide()*
        this.location.x = (float) (this.location.x + direction * (Math.cos(angle) * bulletSpeed));
        this.location.y = (float) (this.location.y + Math.sin(angle) * bulletSpeed);
        this.setPosition(this.location.x, this.location.y);
    }

    //CHapter 7
    public void hit() {
        this.active = false;
        MainGame.MainScene.detachChild(this);

    }

}



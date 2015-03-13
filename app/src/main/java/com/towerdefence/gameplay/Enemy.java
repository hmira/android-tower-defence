package com.towerdefence.gameplay;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.graphics.PointF;

import com.towerdefence.MainGame;


public class Enemy extends AnimatedSprite {
	
	public PointF location;
	public boolean active;
	private float hp;
    private int currentStage = 0;
	
	public Enemy(float pX, float pY, TiledTextureRegion texture) {
		super(pX - 10, pY - 10, texture);
		this.location = new PointF(pX, pY);
		this.active = true;
		hp = 900;
		MainGame.enemyList.add(this);
	}
	
	public void move(float x, float y){
		this.location.x = this.location.x + x;
		this.location.y = this.location.y + y;
		this.setPosition(this.location.x, this.location.y);
	}

    public void move(float x) {
        Direction direction = mapDirections[currentStage];
        switch (direction)
        {
            case RIGHT:
                this.location.x = this.location.x + x;
                this.setPosition(this.location  .x, this.location.y);
                break;

            case LEFT:
                this.location.x = this.location.x - x;
                this.setPosition(this.location.x, this.location.y);
                break;

            case UP:
                this.location.y = this.location.y + x;
                this.setPosition(this.location.x, this.location.y);
                break;

            case DOWN:
                this.location.y = this.location.y - x;
                this.setPosition(this.location.x, this.location.y);
                break;
        }

        PointF dist = new PointF(this.location.x - map[currentStage].x, this.location.y - map[currentStage].y);
        if (dist.length() < x) {
            currentStage++;
        }
    }

	public void takeDamage(float dmg){
		hp = hp - dmg;
		
		if (hp <= 0){
            if (this.active == true) {
                MainGame.money += 10;
                MainGame.updateMoney();
                this.active = false;//
            }
			MainGame.MainScene.detachChild(this);
		}
		
	}

    public boolean isAlive() {
        return hp > 0;
    }

    static final PointF[] map = new PointF[]{
            new PointF(900, 310),
            new PointF(700, 310),
            new PointF(700, 120),
            new PointF(480, 120),
            new PointF(480, 490),
            new PointF(270, 490),
            new PointF(270, 310),
            new PointF(0, 310)
    };

    static final Direction[] mapDirections = new Direction[]{
            Direction.LEFT,
            Direction.LEFT,
            Direction.DOWN,
            Direction.LEFT,
            Direction.UP,
            Direction.LEFT,
            Direction.DOWN,
            Direction.LEFT,
    };

    public enum Direction {
        RIGHT,
        LEFT,
        UP,
        DOWN
    }


}

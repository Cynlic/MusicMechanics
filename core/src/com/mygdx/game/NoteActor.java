package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by harrison on 2/17/17.
 */
public class NoteActor extends DrawableActor{
    private TextureRegion noteHead;
    private Image image;
    private int timeToPlay;
    private float speed = 3;

    public NoteActor(){
        super();
        noteHead = textureAtlas.findRegion("ddr-down-full");
        image = new Image(noteHead);
        timeToPlay = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        image.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta){
        image.setY(speed + image.getY());
    }

    public void setTimeToPlay(int time){
        timeToPlay = time;
    }

    public int getTimeToPlay(){
        return timeToPlay;
    }

}

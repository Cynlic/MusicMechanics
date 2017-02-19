package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by harrison on 2/17/17.
 */
public class ReceiverActor extends DrawableActor {
    TextureRegion noteSpace;
    Image image;
    ReceiverActor(){
        super();
        noteSpace = textureAtlas.findRegion("ddr-down-empty");
        image = new Image(noteSpace);
        image.setY(250f);
    }

    public void draw(Batch batch, float parentAlpha){
        image.draw(batch, parentAlpha);
    }

    public void handleKeyDown(int time){
        System.out.println("Handled the spaces event!");
    }
}

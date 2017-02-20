package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by harrison on 2/17/17.
 */
public class ReceiverActor extends DrawableActor {
    Image image;
    ReceiverActor(){
        super();
        image = new Image(textureAtlas.findRegion("ddr-down-empty"));
        image.setY(500f);
    }

    public void draw(Batch batch, float parentAlpha){
        image.draw(batch, parentAlpha);
    }

    public void handleKeyDown(int time){
        System.out.println("Handled the spaces event!");
    }

    @Override
    public void dispose() {
        super.dispose();
        image.clear();

    }
}

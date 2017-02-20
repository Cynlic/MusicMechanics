package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by harrison on 2/17/17.
 */
public class TestActor extends Actor {
    private TextureRegion textureRegion;
    private Image image;

    public TestActor(){
        textureRegion = new TextureAtlas("ddrsprites.text").findRegion("ddr-down-full");
        image = new Image(textureRegion);
        setHeight(textureRegion.getRegionHeight());
        setWidth(textureRegion.getRegionWidth());
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        image.draw(batch, parentAlpha);
    }
}

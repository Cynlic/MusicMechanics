package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by harrison on 2/17/17.
 */
public class DrawableActor extends Actor {
    final protected TextureAtlas textureAtlas;

    DrawableActor(){
        super();
        textureAtlas = new TextureAtlas("ddrsprites.txt");
    }

    public void dispose(){
        super.clear();
        textureAtlas.dispose();
    }
}

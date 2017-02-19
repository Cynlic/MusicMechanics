package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;

public class MusicTest extends Game{
	Conductor conductor;
	MainGameScreen mainGameScreen;
	
	@Override
	public void create () {
        /*
		conductor = new Conductor();
		System.out.print("Notation: ");
        System.out.println(Arrays.toString(conductor.getBeats().toArray()));
        System.out.print("Beats relative to quantization of 16 beats/whole note: ");
        System.out.println(Arrays.toString(conductor.getBeatsRelative().toArray()));
        System.out.print("Beats to be played in MilliSeconds: ");
        System.out.println(Arrays.toString(conductor.getBeatsInSeconds().toArray()));
        conductor.playSong();
        System.out.println(conductor.songPostion());*/
        mainGameScreen = new MainGameScreen();
        setScreen(mainGameScreen);
	}
	
	@Override
	public void dispose () {
	}
}

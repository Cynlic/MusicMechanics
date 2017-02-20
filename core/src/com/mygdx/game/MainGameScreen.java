package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harrison on 2/13/17.
 */
public class MainGameScreen implements Screen {

    private Conductor conductor;
    private GameActors testActorStage;
    private ScreenViewport viewport;

    MainGameScreen(){
        conductor = new Conductor();
        List<Integer> beats = conductor.getBeatsInSeconds();
        testActorStage = new GameActors(conductor, new ScreenViewport());
        testActorStage.getViewport().setScreenSize(800, 600);
    }

    public void show(){
        Gdx.input.setInputProcessor(testActorStage);
        //conductor.songThread.start();
        /*
        try {
            conductor.songThread.sleep(GameData.leadTime);
        } catch (InterruptedException e){
            System.out.println(e);
        }
        conductor.songThread.play();*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.currentThread().sleep(GameData.leadTime);
                } catch (InterruptedException e) {

                }
                conductor.playSong();
            }
        }).start();
    }

    public void hide(){

    }

    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        testActorStage.act(delta);
        testActorStage.draw();
        //System.out.println(conductor.songPostion());
    }

    public void pause(){

    }

    public void resume(){

    }

    public void resize(int x, int y){
        testActorStage.getViewport().update(800, 600, true);
    }

    public void dispose(){
        testActorStage.dispose();
    }
}

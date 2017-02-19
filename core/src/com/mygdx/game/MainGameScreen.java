package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harrison on 2/13/17.
 */
public class MainGameScreen implements Screen {

    private OrthographicCamera camera;
    private Stage stage;
    private Conductor conductor;
    private NoteActor note;
    private TestActor tester;
    private GameActors testActorStage;

    MainGameScreen(){
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        conductor = new Conductor();
        List<Integer> beats = conductor.getBeatsInSeconds();
        note = new NoteActor();
        tester = new TestActor();
        stage.addActor(note);
        testActorStage = new GameActors(conductor);
    }

    public void show(){
        Gdx.input.setInputProcessor(testActorStage);
        conductor.playSong();
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

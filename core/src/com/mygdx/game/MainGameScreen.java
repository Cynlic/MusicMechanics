package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by harrison on 2/13/17.
 */
public class MainGameScreen implements Screen {

    private Conductor conductor;
    private GameActors testActorStage;
    private Timer mainTimer;
    private Timer.Task testTask;
    private List<Timer.Task> tasks;

    MainGameScreen(){
        conductor = new Conductor();
        List<Integer> beats = conductor.getBeatsInSeconds();
        testActorStage = new GameActors(conductor, new ScreenViewport());
        testActorStage.getViewport().setScreenSize(800, 600);
        mainTimer = new Timer();
        tasks = new ArrayList<>(testActorStage.notesBeforeSound.size());
        for(int i = 0; i < testActorStage.notesBeforeSound.size(); i++) {
            tasks.add(i, new Timer.Task() {
                @Override
                public void run() {
                    testActorStage.addActor(testActorStage.notesBeforeSound.get(0));
                    testActorStage.notesBeforeSound.remove(0);
                }
            });
        }

    }

    public void show(){
        Gdx.input.setInputProcessor(testActorStage);

        for(int i = 0; i < testActorStage.notesBeforeSound.size(); i++){
            mainTimer.scheduleTask(tasks.get(i), ((float)(testActorStage.notesBeforeSound.get(i).getTimeToPlay()+2000)/1000));
            //System.out.println((float)(testActorStage.notesBeforeSound.get(i).getTimeToPlay()+2000)/1000);
        }

        mainTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                conductor.playSong();
                testActorStage.spawnNotes(0.3f);
            }
        },  (float)(2));

        mainTimer.start();
    }

    public void hide(){

    }

    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        testActorStage.spawnNotes(delta);
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
        conductor.dispose();
    }
}

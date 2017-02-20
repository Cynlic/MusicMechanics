package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by harrison on 2/18/17.
 */
public class GameActors extends Stage {
    private ReceiverActor receiverActor;
    private Conductor conductor;
    public List<NoteActor> notes;
    public List<NoteActor> notesBeforeSound;

    public GameActors(Conductor conductor2, Viewport viewport){
        super(viewport);
        conductor = conductor2;
        receiverActor = new ReceiverActor();
        addActor(receiverActor);
        int size = conductor.getBeatsInSeconds().size();
        notes = new ArrayList<>(size);

        for (int i = 0; i < conductor.getBeatsInSeconds().size(); i++){
            NoteActor noteActor = new NoteActor();
            noteActor.setTimeToPlay(conductor.getBeatsInSeconds().get(i));
            //System.out.print("Time to be played: ");
            //System.out.println(noteActor.getTimeToPlay());
            noteActor.setTimeToPlay(conductor.getBeatsInSeconds().get(i)-GameData.leadTime);
            //System.out.print("Time Spawned: ");
            //System.out.println(noteActor.getTimeToPlay());
            notes.add(i, noteActor);
        }

        notesBeforeSound = notes.stream()
                .filter((e) -> (e.getTimeToPlay() < 0))
                .collect(Collectors.toList());

        notes = notes.stream()
                .filter((e) -> (e.getTimeToPlay() >= 0))
                .collect(Collectors.toList());

        this.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode){
                if(keycode == Input.Keys.SPACE){
                    System.out.print("Handled from stage: ");
                    System.out.println(conductor.songPositionMS());
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public void dispose(){
    }

    public void spawnNotes(float delta){
        int deltaBuffer = (int)(delta * 1000);
        Optional<NoteActor> note = notes.stream()
                .filter((e) -> conductor.withinBuffer(conductor.songPositionMS(), e.getTimeToPlay(), deltaBuffer)).findAny();
        if (notes.size()>0 && note.isPresent()){
            addActor(notes.get(0));
            System.out.print("Spawning note at ");
            System.out.println(conductor.songPostion());
            notes.remove(0);
        }
    }

    @Override
    public void act(float delta){
       // spawnNotes(delta);
        super.act(delta);
    }
}

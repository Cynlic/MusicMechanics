package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by harrison on 2/13/17.
 * This is a class to manage a song and its relevant beats. It will need to be reimplemented as songs become more complex
 * but will work for now
 *
 * The class works by transcribing beat patterns in three, yes three, forms of representation.
 *
 * The first is List<noteType> beats, which is a list of the enum noteType. Note type provides almost all rhythmic patterns
 * as a list that looks like [HALF, EIGHTHDOT, SIXTEENTH, QUARTER]. This representation should be able to achieve complete
 * rhythmic parity with a melodic line.
 *
 * The second is List<Float> beatsInSeconds, which is the list of seconds at which each beat should be pressed. This
 * second value should obviously be buffered before it's used to check player input.
 *
 * The third is List<Integer> beatsRelative, which holds the list of "beats" as a set of ints based on a certain value
 * of quantization. Thus, with a list [HALF, EIGHTHDOT, SIXTEENTH QUARTER] where WHOLE = 16 units, the beats would be
 * [0,8,12, 13, 17]. Note here that because of the oddities of musical notation, this list should always be one member longer
 * than List<noteType> beats.
 */
public class Conductor {

    private int BPM; //Beats Per Minute
    private float BPS; // Beats Per Second
    private int QBPMS; // 1/BPS * 1000, that is, Number of seconds per beat quantization
    private int songLengthSeconds;
    private List<noteType> beats; //western standard notation of rhythm up to the 16th note. Uses the enum noteType
    private List<Integer> beatsInSeconds; //Technically milliseconds -- describes when each beat should be played
    private List<Integer> beatsRelative; //which beats should be hit -- described in values of the quantization, so 16th notes for now
    private static int quantization;
    private int beatNumber = 4;
    private String songTitle;
    private Music song;

    private enum noteType{
        WHOLE, WHOLEDOT, HALF, HALFDOT, QUARTER, QUARTERDOT, EIGHTH, EIGHTHDOT, SIXTEENTH,
        WHOLE_REST, HALF_REST, QUARTER_REST, EIGHTH_REST, SIXTEENTH_REST,
        WHOLEDOT_REST, HALFDOT_REST, QUARTERDOT_REST, EIGHTHDOT_REST
    }

    Conductor() {
        BPM = 240;
        BPS = BPM / 60;
        QBPMS = (int)((1/BPS)*1000) ;
        songLengthSeconds = 60;
        quantization = 16;
        beats = new ArrayList<noteType>(beatNumber);
        beats = Arrays.asList(noteType.WHOLE, noteType.WHOLE, noteType.WHOLE, noteType.WHOLE, noteType.WHOLE, noteType.WHOLE);
        beatsRelative = makeRelativeList(beats);
        beatsInSeconds = beatsToMilliseconds(beatsRelative);
        songTitle = "120BPMClick.wav";
        song = Gdx.audio.newMusic(Gdx.files.internal(songTitle));
    }

    public void setBPM(int bpm){
        BPM = bpm;
        BPS = BPM / BPS;
    }

    public void setSongLengthSeconds(int len){
        songLengthSeconds = len;
    }

    public void setSongTitle(String title){
        songTitle = title;
    }

    public void setBeats(List<noteType> coll){
        for(int i = 0; i < coll.size(); i++){
            beats.set(i, coll.get(i));
        }
    }

    private void initBeats(){
        for(int i = 0; i < beats.size(); i++){
            beats.set(i, noteType.WHOLE);
        }
    }

    private static Integer noteTypeToInt(noteType note){
        switch (note){
            case WHOLE:
                return quantization;

            case HALF:
                return quantization/2;

            case QUARTER:
                return quantization/4;

            case EIGHTH:
                return quantization/8;

            case SIXTEENTH:
                return quantization/16;

            case WHOLEDOT:
                return quantization + (quantization/2);

            case HALFDOT:
                return (quantization/2) + (quantization/4);

            case QUARTERDOT:
                return (quantization/4) + (quantization/8);

            case EIGHTHDOT:
                return (quantization/8) + (quantization/16);

            case WHOLE_REST:
                return quantization * 10;

            case HALF_REST:
                return (quantization/2) * 10;

            case QUARTER_REST:
                return (quantization/4) * 10;

            case EIGHTH_REST:
                return (quantization/8) * 10;

            case SIXTEENTH_REST:
                return (quantization/16)* 10;

            case WHOLEDOT_REST:
                return (quantization + (quantization/2) * 10);

            case HALFDOT_REST:
                return ((quantization/2) + (quantization/4) * 10);

            case QUARTERDOT_REST:
                return ((quantization/4) + (quantization/8) * 10);

            case EIGHTHDOT_REST:
                return ((quantization/8) + (quantization/16) * 10);

            default:
                return 0;

        }
    }

    /*
    [quarter-rest eight eight half]
    [16/4*10 16/8 16/8 16/2]
    [40, 2, 2, 8]
    durations to beats
    [4, 6, 8]

    [half sixteen, sixteen, quarterdot half, eight]
    [16/2, 16/16, 16/16, (16/4)+(16/8), 16/2, 16/8,]
    [8, 1, 1, 6, 8, 2]
    [0, 8, 1, 1, 6, 8, 2]
    [0,8,9,10,16, 24, 26]*/


    private static void handleFirstBeat(List<Integer> rhythmBeats) {
            rhythmBeats.add(0, 0);
    }

    private static List<Integer> processRests(List<Integer> rhythmBeats){
        //System.out.println(Arrays.toString(rhythmBeats.toArray()));
        //System.out.println(rhythmBeats.get(0));
        for (int i = 1; i < rhythmBeats.size(); i++){
            //System.out.println(rhythmBeats.get(0));
            int x = rhythmBeats.get(i);
            if (x % 10 == 0){
                rhythmBeats.set(i-1, rhythmBeats.get(i-1)+x/10);
                rhythmBeats.remove(i);
            }
        }
        return rhythmBeats;
    }

    private static List<Integer> sumbeats(List<Integer> rhythmBeats){
        for(int i = 1; i < rhythmBeats.size(); i++){
                rhythmBeats.set(i, rhythmBeats.get(i)+rhythmBeats.get(i-1));
        }
        return rhythmBeats;
    }

    private static List<Integer> intsToRhythmInts (List<Integer> beats){
        List<Integer> rhythmInts;
        rhythmInts = beats;
        handleFirstBeat(rhythmInts);
        rhythmInts = processRests(rhythmInts);
        rhythmInts = sumbeats(rhythmInts);
        return rhythmInts;
    }

    private static List<Integer> makeRelativeList(List<noteType> beats){
        List<Integer> relativeBeats = beats.stream()
                .map(Conductor::noteTypeToInt)
                .collect(Collectors.toList());
        relativeBeats = intsToRhythmInts(relativeBeats);
        return relativeBeats;
    }


    private List<Integer> beatsToMilliseconds(List<Integer> beatsRelative){
        return beatsRelative.stream()
                .map((e) -> QBPMS*e).collect(Collectors.toList());
    }
    public void playSong(){
        song.play();
    }

    public int getBPM(){
        return BPM;
    }

    public float getBPS(){
        return BPS;
    }

    public int getSongLengthSeconds(){
        return songLengthSeconds;
    }

    public List<noteType> getBeats(){
        return beats;
    }

    public List<Integer> getBeatsRelative(){ return beatsRelative;}

    public List<Integer> getBeatsInSeconds(){return beatsInSeconds;}

    public String getSongTitle(){
        return songTitle;
    }


    public float songPostion(){
        float current = song.getPosition();
        return current;
        //return BPS * current;
    }

    public int songPositionMS(){
        return (int)(song.getPosition()*1000);
    }

    public boolean beatsLeftOver (final int currentBeat){
        Optional<Integer> beatsLocal =
                beatsRelative.stream()
                        .filter((e) -> e > currentBeat).findAny();
         if (beatsLocal.isPresent()){
             return true;
         } else {
             return false;
         }
    }

    public boolean withinBuffer(int milliseconds, int beat, int buffer){
        if (milliseconds >= beat-buffer && milliseconds <= beat+buffer) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIfHit (int milliseconds, List<Integer> beats){
        Optional<Integer> possibleBeat = beats.stream()
                .filter((e) -> withinBuffer(milliseconds, e, 250))
                .findAny();

        if (possibleBeat.isPresent()){
            return true;
        } else {
            return false;
        }
    }


}

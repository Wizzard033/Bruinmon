package com.bruinmon;

import java.io.Serializable;
import java.util.*;

public class Bruinmon implements Serializable {

    /** Enumeration for type of the Bruinmon and its moves **/
    public enum Type {
        ROCK, PAPER, SCISSORS, NONE
    }

    /** Gets the name of a particular Bruinmon **/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Gets the image ID of a particular Bruinmon **/
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    /** Gets the description of a particular Bruinmon **/
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /** Gets the location description of a particular Bruinmon **/
    public String getLocationDescription() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    /** Gets the type of a particular Bruinmon **/
    public Type getType() {
        return type;
    }


    public void setType(Type type) {
        this.type = type;
    }
    /** Converts a Type to a String **/
    static public String convertTypeToString(Type type) {
        switch (type) {
            case ROCK :
                return "Rock Type";
            case PAPER :
                return "Paper Type";
            case SCISSORS :
                return "Scissors Type";
        }
        return "Typeless";
    }

    /** Gets the first move of a particular Bruinmon **/
    public Move getMove1() {
        return move1;
    }

    public void setMove1(Move move1) {
        this.move1 = move1;
    }

    /** Gets the second move of a particular Bruinmon **/
    public Move getMove2() {
        return move2;
    }

    public void setMove2(Move move2) {
        this.move2 = move2;
    }

    /** Gets the third move of a particular Bruinmon **/
    public Move getMove3() {
        return move3;
    }

    public void setMove3(Move move3) {
        this.move3 = move3;
    }

    /** Gets the fourth move of a particular Bruinmon **/
    public Move getMove4() {
        return move4;
    }

    public void setMove4(Move move4) {
        this.move4 = move4;
    }

    /** Returns a list containing all Bruinmon **/
    static List<Bruinmon> getAll() {
        if (bruinmon.size() < 1) createBruinmon();
        return Collections.unmodifiableList(bruinmon);
    }

    /** Initializes the list of all Bruinmon **/
    private static void createBruinmon() {
        Bruinmon x = new Bruinmon();
        x.name = "Muscley Bruin";
        x.image = R.drawable.muscle_bruin;
        x.description = "Using its heavy muscles, it throws powerful punches that can send the victim clear over the horizon.";
        x.where = "John Wooden Center";
        x.type = Type.ROCK;
        x.move1 = new Move("Punch", Type.ROCK);
        x.move2 = new Move("Crush", Type.PAPER);
        x.move3 = new Move("Chop", Type.SCISSORS);
        x.move4 = new Move("Throw", Type.NONE);
        MainActivity.bruinDB.addMove(x.getMove1());
        MainActivity.bruinDB.addMove(x.getMove2());
        MainActivity.bruinDB.addMove(x.getMove3());
        MainActivity.bruinDB.addMove(x.getMove4());
        bruinmon.add(x);

        // TODO: Add more Bruinmon
        com.bruinmon.Bruinmon classic = new com.bruinmon.Bruinmon();
        classic.name = "Classic Bruin";
        classic.image = R.drawable.classic_bruin;
        classic.description = "An all-around fighter.";
        classic.where = "John Wooden Center";
        classic.type = com.bruinmon.Bruinmon.Type.ROCK;
        classic.move1 = new Move("Punch", com.bruinmon.Bruinmon.Type.ROCK);
        classic.move2 = new Move("Throw", com.bruinmon.Bruinmon.Type.NONE);
        classic.move3 = new Move("Chop", com.bruinmon.Bruinmon.Type.SCISSORS);
        classic.move4 = new Move("Crush", com.bruinmon.Bruinmon.Type.PAPER);
        bruinmon.add(classic);

        com.bruinmon.Bruinmon cheerful = new com.bruinmon.Bruinmon();
        cheerful.name = "Cheerful Bruin";
        cheerful.image = R.drawable.cheerful_bruin;
        cheerful.description = "A cheerful bruin that would rather do an 8-clap than fight.";
        cheerful.where = "Rose Bowl";
        cheerful.type = com.bruinmon.Bruinmon.Type.PAPER;
        cheerful.move1 = new Move("Punch", com.bruinmon.Bruinmon.Type.ROCK);
        cheerful.move2 = new Move("Kick", com.bruinmon.Bruinmon.Type.ROCK);
        cheerful.move3 = new Move("8-Clap", com.bruinmon.Bruinmon.Type.NONE);
        cheerful.move4 = new Move("Cheer", com.bruinmon.Bruinmon.Type.NONE);
        bruinmon.add(cheerful);

        com.bruinmon.Bruinmon basketball = new com.bruinmon.Bruinmon();
        basketball.name = "Big Baller Bruin";
        basketball.image = R.drawable.basketball_bruin;
        basketball.description = "Using its big baller skills, it can match any foe, on or off the court.";
        basketball.where = "Pauley Pavilion";
        basketball.type = com.bruinmon.Bruinmon.Type.ROCK;
        basketball.move1 = new Move("Punch", com.bruinmon.Bruinmon.Type.ROCK);
        basketball.move2 = new Move("Kick", com.bruinmon.Bruinmon.Type.ROCK);
        basketball.move3 = new Move("Dribble", com.bruinmon.Bruinmon.Type.PAPER);
        basketball.move4 = new Move("Box-Out", com.bruinmon.Bruinmon.Type.ROCK);
        bruinmon.add(basketball);
    }

    /** Returns a list containing all Bruinmon that are owned by the user **/
    //static List<Bruinmon> getAllOwned() {
       // return Collections.unmodifiableList(ownedBruinmon);
    //}

    /** Adds a Bruinmon to the list of owned Bruinmon and returns false if the Bruinmon was already captured before **/
    static boolean captureBruinmon(Bruinmon bruinmon, MoveDBOperater bruinmonDb) {
        List<Bruinmon> ownedBruinmon = bruinmonDb.getAllBruinmons();
        for(Bruinmon mon: ownedBruinmon){
            if(mon.getName().equals(bruinmon.getName())){
                return false;
            }
        }
        bruinmonDb.addBruinmon(bruinmon);
        return true;
    }

    private String name;
    private int image;
    private String description;
    private String where;
    private Type type;
    private Move move1;
    private Move move2;
    private Move move3;
    private Move move4;
    // TODO: Add additional variables to define Bruinmons' locations (for GPS tech)

    private static List<Bruinmon> bruinmon = new ArrayList<Bruinmon>();
    //private static List<Bruinmon> ownedBruinmon = new ArrayList<Bruinmon>();
}

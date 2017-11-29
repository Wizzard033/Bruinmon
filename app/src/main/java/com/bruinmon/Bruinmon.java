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

    /** Gets the image ID of a particular Bruinmon **/
    public int getImage() {
        return image;
    }

    /** Gets the description of a particular Bruinmon **/
    public String getDescription() {
        return description;
    }

    /** Gets the location description of a particular Bruinmon **/
    public String getLocationDescription() {
        return where;
    }

    /** Gets the type of a particular Bruinmon **/
    public Type getType() {
        return type;
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

    /** Gets the second move of a particular Bruinmon **/
    public Move getMove2() {
        return move2;
    }

    /** Gets the third move of a particular Bruinmon **/
    public Move getMove3() {
        return move3;
    }

    /** Gets the fourth move of a particular Bruinmon **/
    public Move getMove4() {
        return move4;
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
        bruinmon.add(x);

        // TODO: Add more Bruinmon
    }

    /** Returns a list containing all Bruinmon that are owned by the user **/
    static List<Bruinmon> getAllOwned() {
        return Collections.unmodifiableList(ownedBruinmon);
    }

    /** Adds a Bruinmon to the list of owned Bruinmon and returns false if the Bruinmon was already captured before **/
    static boolean captureBruinmon(Bruinmon bruinmon) {
        if (ownedBruinmon.contains(bruinmon)) {
            return false;
        }
        ownedBruinmon.add(bruinmon);
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
    private static List<Bruinmon> ownedBruinmon = new ArrayList<Bruinmon>();
}

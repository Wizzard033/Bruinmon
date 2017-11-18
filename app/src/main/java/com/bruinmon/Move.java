package com.bruinmon;

public class Move {

    /** Move constructor **/
    Move(String name, Bruinmon.Type type) {
        this.name = name;
        this.type = type;
    }

    /** Gets the name of a particular move **/
    public String getName() {
        return name;
    }

    /** Gets the type of a particular move **/
    public Bruinmon.Type getType() {
        return type;
    }

    private String name;
    private Bruinmon.Type type;
}

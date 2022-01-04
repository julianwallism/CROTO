package code_generation;

import java.util.ArrayList;

public class Procedure extends Element {

    String name, initial; // label
    ArrayList<String> params;

    public Procedure(String name, String initial, ArrayList<String> params) {
        this.name = name;
        this.initial = initial;
        this.params = params;
    }
}
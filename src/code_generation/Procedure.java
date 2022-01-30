/**
 * Practica Final Compiladores - 2021/2022
 * 
 * Jonathan Salisbury Vega
 * Julián Wallis Medina
 * Joan Sansó Pericàs
 */
package code_generation;

import java.util.ArrayList;

public class Procedure {

    String start; // label
    ArrayList<String> params;

    public Procedure(String initial, ArrayList<String> params) {
        this.start = initial;
        this.params = params;
    }
}
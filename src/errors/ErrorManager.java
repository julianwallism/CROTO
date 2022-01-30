/**
 * Practica Final Compiladores - 2021/2022
 * 
 * Jonathan Salisbury Vega
 * Julián Wallis Medina
 * Joan Sansó Pericàs
 */
package errors;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class ErrorManager {
    
    private FileWriter fw = null;
    private BufferedWriter bw = null;
    
    public void open(String path){
        try {
            this.fw = new FileWriter(path + "_errors.txt");
            this.bw = new BufferedWriter(this.fw);
        } catch (IOException ex) {
            System.err.println("Error when writing to Errors.txt + " + ex.toString());
        }  
    }
    
    public void writeError(String error){
        try {
            this.bw.write(error);
            this.bw.newLine();
        } catch (IOException ex) {
            System.err.println("Error when writing to Errors.txt + " + ex.toString());
        }  
        System.err.println(error);
    }
    
    public void writeError(CrotoException exception){
        try {
            this.bw.write(exception.toString());
            this.bw.newLine();
        } catch (IOException ex) {
            System.err.println("Error when writing to Errors.txt + " + ex.toString());
        }  
        System.err.println(exception);
    }
    
    public void closeManager(){
        try {
            this.bw.close();
            this.fw.close();
        } catch (IOException ex) {
            System.err.println("Error when writing to Errors.txt + " + ex.toString());
        }
    }
}

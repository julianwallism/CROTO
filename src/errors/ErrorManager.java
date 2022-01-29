/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package errors;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author elsho
 */
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
    
    public void closeManager(){
        try {
            this.bw.close();
            this.fw.close();
        } catch (IOException ex) {
            System.err.println("Error when writing to Errors.txt + " + ex.toString());
        }
    }
}

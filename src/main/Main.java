/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;
import lexic.Lexer;
import sintactic.parser;

/**
 *
 * @author elsho, walli, sans
 */
public class Main {

    private static void lexerTester() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Lexer lex = new Lexer(br);
        System.out.println("Introduce codigo:");
        while (true) {
            try {
                Symbol s = lex.next_token();
                System.out.print(s + " ");
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private static void sintacticTester() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Lexer lex = new Lexer(br);
            System.out.println("Introduce codigo:");
            parser p = new parser(lex);
            p.parse();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        sintacticTester();
    }
}

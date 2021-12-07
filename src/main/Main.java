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
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;
import lexic.Lexer;
import sintactic.parser;

/**
 *
 * @author elsho, walli, sans
 */
public class Main {

    private static void lexerTester() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ComplexSymbolFactory symFact = new ComplexSymbolFactory();
        Lexer lex = new Lexer(br, symFact);
        System.out.println("Introduce codigo:");
        while (true) {
            try {
                ComplexSymbol s = (ComplexSymbol) lex.next_token();
                
                System.out.print(s.getName() + " ");
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private static void sintacticTester() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Lexer lex = new Lexer(br);
            SymbolFactory symFact = new ComplexSymbolFactory();
            parser p = new parser(lex, symFact);
            System.out.println("Introduce codigo:");
            p.parse();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        lexerTester();
    }
}

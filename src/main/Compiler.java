/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import code_generation.CodeGenerator;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import lexic.LexicScanner;
import semantic.SemanticAnalyzer;
import semantic.symbols.Program;
import sintactic.SyntaxParser;
import sintactic.symbols.CrotoSymbolFactory;
import org.apache.commons.cli.*;

/**
 *
 * @author elsho, walli, sans
 */
public class Compiler {
    
    private FileReader fr;
    private BufferedReader reader;
    private final CrotoSymbolFactory symFact;
    
    private LexicScanner scanner;
    private SyntaxParser parser;
    private SemanticAnalyzer analyzer;
    
    private CodeGenerator generator;
    
    private Program prog;
    
    public Compiler() {
        this.symFact = new CrotoSymbolFactory();
    }
    
    public void compile(String inFile, String outFile){
        this.scan(inFile);
        this.parse();
        this.analyze(outFile);
        this.generate(outFile);
    }
    
    private void scan(String filename){
        try {
            this.fr = new FileReader(filename);
            this.reader = new BufferedReader(fr);
        } catch (FileNotFoundException ex) {
            System.err.println(ex.toString());
        }
        this.scanner = new LexicScanner(reader, symFact);
    }
    
    private void parse(){
        this.parser = new SyntaxParser(scanner, symFact);
        try {
            this.prog = (Program) this.parser.parse().value;
            if(this.parser.error){
                System.exit(0);
            }
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }                
    
    private void analyze(String filename){
        this.analyzer = new SemanticAnalyzer();
        this.analyzer.openFile(filename);
        prog.check(this.analyzer);
        this.analyzer.closeFile();
        if(this.analyzer.error){
            System.exit(0);
        }
    }
    
    private void generate(String outFile){
        this.generator = new CodeGenerator();
        this.generator.generate3ac(this.prog);
        this.generator.write3ac(outFile);
        this.generator.generateAssembly();
        this.generator.writeAssembly(outFile);
    }
    
    private static void generationTester(String filename) {
        try {
            FileReader fr = new FileReader("test/" + filename + ".croto");
            System.out.println("Reading and scanning source file...");
            BufferedReader br = new BufferedReader(fr);
            CrotoSymbolFactory symFact = new CrotoSymbolFactory();
            LexicScanner lex = new LexicScanner(br, symFact);
            SyntaxParser p = new SyntaxParser(lex, symFact);
            System.out.println("Parsing source code...");
            Program program = (Program) p.parse().value;
            System.out.println("Analyzing source code...");
            SemanticAnalyzer s = new SemanticAnalyzer();
            s.openFile(filename);
            program.check(s);
            s.closeFile();
            if (s.error) {
                System.err.println("Errors found. Compilation stopped.");
            } else {
                CodeGenerator cg = new CodeGenerator();
                System.out.println("Generating 3ac...");
                cg.generate3ac(program);
                System.out.println("Writing 3ac to file...");
                cg.write3ac("test/" + filename);
                cg.generateAssembly();
                cg.writeAssembly("test/" + filename);
            }
        } catch (Exception ex) {
            Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Usage: java -jar Croto.jar [input file] [output file] 
     * 
     * @param args 
     */
    public static void main(String[] args) { 
//        String inFile = args[0];
//        String outFile = args[1];
//        
//        Compiler comp = new Compiler();
//        comp.compile(inFile, outFile);
        generationTester("Test");
    }
    
//    public static void argParsing(String[] args){
//        Options options = new Options();
//        options.addOption("3ac", false, "generate Three Address Code output file");
//    }
}

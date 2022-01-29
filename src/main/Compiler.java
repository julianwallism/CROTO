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

/**
 *
 * @author elsho, walli, sans
 */
public class Compiler {

    private static final int OPT_LVL = 10;

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

    public void compile(String inFile, String outFile) {
        this.scan(inFile);
        this.parse();
        this.analyze(outFile);
        this.generate(outFile);
        System.out.println("File Compiled Succesfully.");
    }

    private void scan(String filename) {
        try {
            this.fr = new FileReader(filename);
            this.reader = new BufferedReader(fr);
        } catch (FileNotFoundException ex) {
            System.err.println(ex.toString());
        }
        this.scanner = new LexicScanner(reader, symFact);
    }

    private void parse() {
        this.parser = new SyntaxParser(scanner, symFact);
        try {
            this.prog = (Program) this.parser.parse().value;
            if (this.parser.error) {
                System.exit(0);
            }
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }

    private void analyze(String filename) {
        this.analyzer = new SemanticAnalyzer();
        this.analyzer.openFile(filename);
        prog.check(this.analyzer);
        this.analyzer.closeFile();
        if (this.analyzer.error) {
            System.exit(0);
        }
    }

    private void generate(String outFile) {
        this.generator = new CodeGenerator();
        this.generator.generate3ac(this.prog);
        /*this.generator.optimize(OPT_LVL);
        System.out.println("optimizing code");*/
        this.generator.write3ac(outFile);
        this.generator.generateAssembly();
        this.generator.writeAssembly(outFile);
    }

    /**
     * Usage: java -jar Croto.jar [input file] [output file]
     * 
     * @param args
     */
    public static void main(String[] args) {
        // String inFile = args[0];
        // String outFile = args[1];

        Compiler comp = new Compiler();
        comp.compile("test/Incorrectes/Programa1/Programa1.croto", "test/Incorrectes/Programa1/Programa1");
        // generationTester("Test");
    }
}

package main;

import java.io.File;

import components.FileNode;
import middleware.NameTable;
import parser.MINIGrammar;
import testsuite.MINIException;
import visitors.JavaCodeGenerator;
import visitors.NameAndTypeChecker;

public class Main {

    public static void main(String... args) {
        NameTable globalNameTable = new NameTable(null);
        FileNode classes = null;
        try {
            File sourceFile = new File("res" + File.separator + "example_code" + File.separator + "valid"
                    + File.separator + "pretty_much_everything.m");
            classes = MINIGrammar.parse(sourceFile);
            NameAndTypeChecker checker = new NameAndTypeChecker();
            classes.accept(checker, globalNameTable);
            JavaCodeGenerator javaPrinter = new JavaCodeGenerator();
            classes.accept(javaPrinter, null);
            System.out.println(javaPrinter.toString());
        } catch (MINIException e) {
            e.printStackTrace();
        }

    }
}
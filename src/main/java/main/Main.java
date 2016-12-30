package main;

import java.io.File;

import components.FileNode;
import ir.NameTable;
import parser.MINIGrammar;
import testsuite.MINIException;
import visitors.ByteCodeGenerator;
import visitors.JavaCodeGenerator;
import visitors.NameAndTypeChecker;

public class Main {

    public static void main(String... args) {
        FileNode classes;
        try {
            File sourceFile = new File("res" + File.separator + "example_code" + File.separator + "valid"
                    + File.separator + "pretty_much_everything.m");
            classes = MINIGrammar.parse(sourceFile);
            NameAndTypeChecker checker = new NameAndTypeChecker();
            classes.accept(checker, null);
            JavaCodeGenerator javaPrinter = new JavaCodeGenerator();
            classes.accept(javaPrinter, null);
//            System.out.println(javaPrinter.toString());
            ByteCodeGenerator generator = new ByteCodeGenerator();
            classes.accept(generator, null);
        } catch (MINIException e) {
            e.printStackTrace();
        }

    }
}
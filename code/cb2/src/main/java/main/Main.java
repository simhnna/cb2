package main;

import java.io.File;

import components.FileNode;
import parser.MINIGrammar;
import testsuite.MINIException;
import visitors.NameAndTypeChecker;
import visitors.PrettyPrinter;

public class Main {

    public static void main(String... args) {
        try {
            File sourceFile = new File("res" + File.separator + "example_code" + File.separator + "valid"
                    + File.separator + "pretty_much_everything.m");
            FileNode classes = MINIGrammar.parse(sourceFile);
            PrettyPrinter prettyPrinter = new PrettyPrinter();
            classes.accept(prettyPrinter, null);
            System.out.println(prettyPrinter.toString());
            NameAndTypeChecker checker = new NameAndTypeChecker(sourceFile);
            classes.accept(checker, null);
        } catch (MINIException e) {
            e.printStackTrace();
        }
    }
}
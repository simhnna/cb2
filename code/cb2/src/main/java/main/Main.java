package main;

import java.io.File;
import java.util.ArrayList;

import components.ClassNode;
import parser.MINIGrammar;
import testsuite.MINIException;
import visitors.PrettyPrinter;

public class Main {

    public static void printPretty(ArrayList<ClassNode> parse_result) {
        PrettyPrinter visitor = new PrettyPrinter();
        for (ClassNode cls : parse_result) {
            cls.accept(visitor);
        }
        System.out.println(visitor.toString());
    }

    public static void main(String... args) {
        ArrayList<ClassNode> classes;
        try {
            classes = MINIGrammar.parse(new File("res" + File.separator + "example_code" + File.separator + "valid"
                    + File.separator + "pretty_much_everything.m"));
            printPretty(classes);
        } catch (MINIException e) {
            e.printStackTrace();
        }
    }
}
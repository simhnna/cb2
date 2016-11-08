package main;

import java.io.File;
import java.util.ArrayList;

import components.ClassNode;
import parser.MINIGrammar;
import testsuite.MINIException;
import visitors.ASTVisitor;

public class Main {

    public static void printClassesAndMembers(ArrayList<ClassNode> parse_result) {
        for (ClassNode cls: parse_result) {
            System.out.println(cls);
            cls.children.forEach(System.out::println);
        }
    }

    public static void printPretty(ArrayList<ClassNode> parse_result) {
        ASTVisitor visitor = new ASTVisitor();
        for (ClassNode cls: parse_result) {
            cls.accept(visitor);
        }
        System.out.println(visitor.toString());
    }

    public static void main(String... args) {
        ArrayList<ClassNode> classes;
        try {
            classes = MINIGrammar.parse(new File("res" + File.separator + "example_code" + File.separator + "valid" + File.separator + "pretty_much_everything.m"));
//            printClassesAndMembers(classes);
            printPretty(classes);
        } catch (MINIException e) {
            e.printStackTrace();
        }
    }
}
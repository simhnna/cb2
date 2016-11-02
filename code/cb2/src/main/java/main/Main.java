package main;

import java.io.File;
import java.util.ArrayList;

import components.ClassNode;
import components.Node;
import parser.MINIGrammar;
import testsuite.MINIException;
import visitors.ASTVisitor;

public class Main {

    public static void printClassesAndMembers(ArrayList<ClassNode> parse_result) {
        for (ClassNode cls: parse_result) {
            System.out.println(cls);
            for (Node child : cls.children) {
                System.out.println(child);
            }
        }
    }

    public static void printPretty(ArrayList<ClassNode> parse_result) {
        for (ClassNode cls: parse_result) {
            cls.accept(new ASTVisitor());
        }
    }

    public static void main(String... args) {
        ArrayList<ClassNode> classes;
        try {
            classes = MINIGrammar.parse(new File("res" + File.separator + "example_code" + File.separator + "valid" + File.separator + "beispiele_timm.m"));
//            printClassesAndMembers(classes);
            printPretty(classes);
        } catch (MINIException e) {
            e.printStackTrace();
        }
    }
}
package main;

import java.io.File;
import java.util.ArrayList;

import components.ClassNode;
import components.Node;
import parser.MINIGrammar;
import testsuite.MINIException;

public class Main {

    public boolean someLibraryMethod() {
        return true;
    }


    public static void main(String... args) {
        ArrayList<ClassNode> classes;
        try {
            classes = MINIGrammar.parse(new File("res" + File.separator + "example_code" + File.separator + "valid" + File.separator + "beispiele_timm.m"));
            for (ClassNode cls: classes) {
                System.out.println(cls);
                for (Node child : cls.children) {
                    System.out.println(child);
                }
            }
        } catch (MINIException e) {
            e.printStackTrace();
        }
    }
}

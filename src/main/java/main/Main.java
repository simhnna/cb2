package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import components.FileNode;
import ir.NameTable;
import org.apache.bcel.classfile.JavaClass;
import parser.MINIGrammar;
import testsuite.MINIException;
import visitors.ByteCodeGenerator;
import visitors.JavaCodeGenerator;
import visitors.NameAndTypeChecker;

public class Main {

    public static void main(String... args) throws IOException {
        FileNode classes;
        try {
            File sourceFile = new File("res" + File.separator + "example_code" + File.separator + "valid"
                    + File.separator + "loop.m");
            classes = MINIGrammar.parse(sourceFile);
            NameAndTypeChecker checker = new NameAndTypeChecker();
            classes.accept(checker, null);
            JavaCodeGenerator javaPrinter = new JavaCodeGenerator();
            classes.accept(javaPrinter, null);
//            System.out.println(javaPrinter.toString());
            ByteCodeGenerator generator = new ByteCodeGenerator();
            ArrayList<JavaClass> genClasses = (ArrayList<JavaClass>) classes.accept(generator, null);

            for (JavaClass cls: genClasses) {
                cls.dump(new File("/tmp/a.class"));
            }
        } catch (MINIException e) {
            e.printStackTrace();
        }

    }
}
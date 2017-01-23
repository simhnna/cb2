package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import components.FileNode;
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
                    + File.separator + "program_to_test_byte_code_generation.m");
            classes = MINIGrammar.parse(sourceFile);
            NameAndTypeChecker checker = new NameAndTypeChecker();
            classes.accept(checker, null);
            JavaCodeGenerator javaPrinter = new JavaCodeGenerator();
            classes.accept(javaPrinter, null);
//            System.out.println(javaPrinter.toString());
            ByteCodeGenerator generator = new ByteCodeGenerator();
            classes.accept(generator, null);
            File tmpDir = Files.createTempDirectory("classFiles").toFile();
            for (JavaClass cls: generator.getGeneratedClasses()) {
                cls.dump(new File(tmpDir + File.separator + cls.getClassName() + ".class"));
            }
        } catch (MINIException e) {
            e.printStackTrace();
        }
    }
}
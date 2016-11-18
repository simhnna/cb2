package frontend;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

import components.ClassNode;
import parser.MINIGrammar;
import testsuite.MINIException;
import visitors.ASTVisitor;

public class MINIParser {
    public static void wortproblem(File in) throws MINIException {
        MINIGrammar.parse(in);
    }

    public static void prettyPrinted(File in, File out) throws MINIException {
        ArrayList<ClassNode> classes = MINIGrammar.parse(in);
        ASTVisitor visitor = new ASTVisitor();
        classes.forEach((cls) -> cls.accept(visitor));
        try {
            ArrayList<String> output = new ArrayList<>();
            output.add(visitor.toString());
            Files.write(out.toPath(), output, Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.err.println("File not writable.");
            e.printStackTrace();
        }
    }
}

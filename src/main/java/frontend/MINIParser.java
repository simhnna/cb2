package frontend;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import components.FileNode;
import org.apache.bcel.classfile.JavaClass;
import parser.MINIGrammar;
import parser.Token;
import parser.TokenMgrError;
import testsuite.MINIException;
import testsuite.ParseException;
import visitors.ByteCodeGenerator;
import visitors.JavaCodeGenerator;
import visitors.NameAndTypeChecker;
import visitors.PrettyPrinter;

public class MINIParser {
    
    public static void handleParseError(File f, Token currentToken, String[] tokenImage, int[][] expectedTokenSequences)
            throws MINIException {
        int line = currentToken.next.beginLine;
        int col = currentToken.next.beginColumn;
        if (expectedTokenSequences.length > 2) {
            throw new ParseException(f, line, "col:" + col + " did not expect '" + currentToken.next.image + "'");
        } else {
            StringBuilder bldr = new StringBuilder("col:" + col + " -> was expecting ");
            for (int i = 0; i < expectedTokenSequences.length; ++i) {
                for (int j = 0; j < expectedTokenSequences[i].length; ++j) {
                    bldr.append(tokenImage[expectedTokenSequences[i][j]]).append(" ");
                }
                if (i < expectedTokenSequences.length - 1) {
                    bldr.append("or ");
                }
            }
            throw new ParseException(f, line, bldr.toString());
        }
    }

    public static void handleTokenMgrError(File f, TokenMgrError e) throws MINIException {
        throw new ParseException(f, 0, e.getMessage());
    }
    
    public static void wortproblem(File in) throws MINIException {
        MINIGrammar.parse(in);
    }

    private static void printLinesToFile(File file, String lines) {
        try {
            Files.write(file.toPath(), lines.getBytes(Charset.forName("UTF-8")), StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void prettyPrinted(File in, File out) throws MINIException {
        FileNode classes = MINIGrammar.parse(in);
        PrettyPrinter visitor = new PrettyPrinter();
        classes.accept(visitor, null);
        printLinesToFile(out, visitor.toString());
    }

    public static void printJavaSource(File in, File out) throws MINIException {
        FileNode classes = MINIGrammar.parse(in);
        NameAndTypeChecker checker = new NameAndTypeChecker();
        JavaCodeGenerator java_code_gen = new JavaCodeGenerator();
        classes.accept(checker, null);
        classes.accept(java_code_gen, null);
        printLinesToFile(out, java_code_gen.toString());
    }
    
    public static void isMINI(File in) throws MINIException {
        FileNode classes = MINIGrammar.parse(in);
        NameAndTypeChecker checker = new NameAndTypeChecker();
        classes.accept(checker, null);
    }

    public static void generateByteCode(File in, File out) throws MINIException {
        FileNode classes = MINIGrammar.parse(in);
        NameAndTypeChecker checker = new NameAndTypeChecker();
        ByteCodeGenerator generator = new ByteCodeGenerator();
        classes.accept(checker, null);
        ArrayList<JavaClass> generatedClasses = (ArrayList<JavaClass>) classes.accept(generator, null);
        for (JavaClass cls: generatedClasses) {
            try {
                cls.dump(new File(out.getAbsolutePath() + cls.getClassName() + ".class"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}

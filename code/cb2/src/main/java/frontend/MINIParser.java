package frontend;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

import components.FileNode;
import parser.MINIGrammar;
import parser.Token;
import parser.TokenMgrError;
import testsuite.MINIException;
import testsuite.ParseException;
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

    public static void prettyPrinted(File in, File out) throws MINIException {
        FileNode classes = MINIGrammar.parse(in);
        PrettyPrinter visitor = new PrettyPrinter();
        classes.accept(visitor, null);
        try {
            ArrayList<String> output = new ArrayList<>();
            output.add(visitor.toString());
            Files.write(out.toPath(), output, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printJavaSource(File in, File out) throws MINIException {
        FileNode classes = MINIGrammar.parse(in);
        NameAndTypeChecker checker = new NameAndTypeChecker();
        JavaCodeGenerator java_code_gen = new JavaCodeGenerator();
        classes.accept(checker, null);
        classes.accept(java_code_gen, null);
        try {
            ArrayList<String> output = new ArrayList<>();
            output.add(java_code_gen.toString());
            Files.write(out.toPath(), output, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

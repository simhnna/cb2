package errorHandling;

import java.io.File;

import parser.Token;
import testsuite.MINIException;
import testsuite.ParseException;

public class ErrorHandler {

    public static void handleParseError(File f, Token currentToken, String[] tokenImage, int[][] expectedTokenSequences)
            throws MINIException {
        int line = currentToken.next.beginLine;
        int col = currentToken.next.beginColumn;
        if (expectedTokenSequences.length > 2) {
            throw new ParseException(f, line, "col:" + col + " did not expect '" + currentToken.next.image +"'");
        } else {
            StringBuilder bldr = new StringBuilder("col:" + col + " -> was expecting ");
            for (int i = 0; i < expectedTokenSequences.length; ++i) {
                for (int j = 0; j < expectedTokenSequences[i].length; ++j) {
                    bldr.append(tokenImage[expectedTokenSequences[i][j]] + " ");
                }
                if (i < expectedTokenSequences.length - 1) {
                    bldr.append("or ");
                }
            }
            throw new ParseException(f, line, bldr.toString());
        }
    }
}

package errorHandling;

import java.io.File;

import parser.Token;
import testsuite.MINIException;
import testsuite.TypeException;

public class ErrorHandler {

	public static void handleParseError(File f, Token currentToken, String[] tokenImage, int[][] expectedTokenSequences) throws MINIException {
		int line = currentToken.next.beginLine;
		int col = currentToken.next.beginColumn;
		StringBuilder bldr = new StringBuilder("col:" + col +" -> was expecting ");
		for (int i = 0; i < expectedTokenSequences.length; ++i) {
			for (int j = 0; j < expectedTokenSequences[i].length; ++j) {
				bldr.append(tokenImage[expectedTokenSequences[i][j]] + " ");
			}
			if (i < expectedTokenSequences.length - 1) {
				bldr.append("or ");
			}
		}
		throw new TypeException(f, line, bldr.toString());
	}
}

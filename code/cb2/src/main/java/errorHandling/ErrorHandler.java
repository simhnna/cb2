package errorHandling;

import java.io.File;

import parser.ParseException;
import testsuite.MINIException;
import testsuite.TypeException;

public class ErrorHandler {

	public static void handleParseError(File f, ParseException e) throws MINIException {
		String file;
		int line = e.currentToken.next.beginLine;
		int column = e.currentToken.next.beginColumn;
		System.err.println(">>>>>>>>>>>");
		e.printStackTrace();
		System.err.println("<<<<<<<<<<<");
		throw new TypeException(f, line, "error");
	}
}

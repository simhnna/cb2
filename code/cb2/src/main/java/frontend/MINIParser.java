package frontend;

import java.io.File;

import parser.MINIGrammar;
import testsuite.MINIException;

public class MINIParser {
	public static void wortProblem (File in) throws MINIException {
		MINIGrammar.parse(in);
	}
}

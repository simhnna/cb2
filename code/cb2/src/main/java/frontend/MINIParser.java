package frontend;

import java.io.File;

import parser.MINIGrammar;
import testsuite.MINIException;

public class MINIParser {
	public static void wortproblem (File in) throws MINIException {
		MINIGrammar.parse(in);
	}
}

package frontend;

import java.io.File;
import java.util.ArrayList;

import components.ClassNode;
import parser.MINIGrammar;
import testsuite.MINIException;

public class MINIParser {
	public static void wortproblem (File in) throws MINIException {
		MINIGrammar.parse(in);
	}

	public static void prettyPrinted(File in, File out) throws MINIException {
	    ArrayList<ClassNode> classes = MINIGrammar.parse(in);
	}
}

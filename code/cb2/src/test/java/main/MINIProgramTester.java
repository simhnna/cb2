package main;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

import parser.MINIGrammar;
import parser.ParseException;
import parser.TokenMgrError;


public class MINIProgramTester {
	@Test public void testValidPrograms() {
		File validFolder = new File("res/example_code/valid");
		File[] sourceFiles = validFolder.listFiles();
		for (File f: sourceFiles) {
			if (f.isFile() && f.getName().endsWith(".m")) {
				try {
					MINIGrammar parser = new MINIGrammar(new FileInputStream(f));
					parser.file();
				} catch (FileNotFoundException e) {
					// SHOULD NOT HAPPEN
					e.printStackTrace();
				} catch (ParseException e) {
					// TEST FAILURE
					assertFalse("Failed to parse file " + f.getAbsolutePath(), true);
					e.printStackTrace();
				}
			}
		}
	}
	
	@Test public void testInvalidPrograms() {
		File validFolder = new File("res/example_code/invalid");
		File[] sourceFiles = validFolder.listFiles();
		for (File f: sourceFiles) {
			if (f.isFile() && f.getName().endsWith(".m")) {
				try {
					MINIGrammar parser = new MINIGrammar(new FileInputStream(f));
					parser.file();
					assertFalse("Successfully parsed file that should contain errors " + f.getAbsolutePath(), true);
				} catch (FileNotFoundException e) {
					// SHOULD NOT HAPPEN
					e.printStackTrace();
				} catch (ParseException|TokenMgrError e) {
					// TEST SUCCESS
				}
			}
		}
	}
}

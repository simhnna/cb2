package main;

import java.io.File;
import java.util.ArrayList;

import components.ClassNode;
import components.Node;
import parser.MINIGrammar;
import testsuite.MINIException;

public class Main {

	public boolean someLibraryMethod() {
		return true;
	}

	public static void main(String... args) {
		ArrayList<ClassNode> classes;
		try {
			classes = MINIGrammar.parse(new File("res/example_code/valid/testing_file.m"));
			for (ClassNode cls: classes) {
				System.out.println(cls);
				for (Node child : cls.children) {
					System.out.println(child);
				}
			}
		} catch (MINIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

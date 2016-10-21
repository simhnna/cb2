package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import components.ClassNode;
import components.FieldNode;
import components.MethodNode;
import components.Node;
import parser.MINIGrammar;
import parser.ParseException;

public class Main {

	public boolean someLibraryMethod() {
		return true;
	}

	public static void main(String... args) {
		ArrayList<ClassNode> classes;
		try {
			classes = MINIGrammar.parse(new FileInputStream(new File("res/example_code/valid/testing_file.m")));
			for (ClassNode cls: classes) {
				System.out.println(cls.name);
				for (Node child : cls.children) {
					if (child instanceof FieldNode) {
						FieldNode field = (FieldNode) child;
						System.out.println(field);
					} else if (child instanceof MethodNode) {
						MethodNode method = (MethodNode) child;
						System.out.println(method);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

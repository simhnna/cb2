package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import components.ClassNode;
import parser.MINIGrammar;
import parser.ParseException;

public class Main {
	
    public boolean someLibraryMethod() {
        return true;
    }
    
    public static void main(String... args) {
    	System.out.println("Hello World!");
    	ClassNode n;
    	try {
			n = MINIGrammar.parse(new FileInputStream(new File("/tmp/meh.m")));
			System.out.println(n.name);
			System.out.println(n.id);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

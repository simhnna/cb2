package main;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import components.ClassNode;
import parser.MINIGrammar;
import testsuite.MINIException;
import visitors.ASTVisitor;

@RunWith(Parameterized.class)
public class PrettyPrinterTester {

    private File file;

    public PrettyPrinterTester(File file, String filename) {
        this.file = file;
    }

    @Test
    public void testPrettyPrinter() {
        try {
            ArrayList<ClassNode> classes = MINIGrammar.parse(file);
            ASTVisitor visitor = new ASTVisitor();
            classes.forEach((cls) -> {cls.accept(visitor);});
            List<String> computedStrings = Arrays.asList(visitor.toString().split("\n"));
            List<String> actualStrings = Files.readAllLines(file.toPath());
            assertEquals(actualStrings.size(), computedStrings.size());
            for (int i = 0; i < actualStrings.size(); ++i) {
                assertEquals(actualStrings.get(i), computedStrings.get(i));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MINIException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Parameters(name="{1}")
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<Object[]>();
        File validFolder = new File("res" + File.separator + "example_code" + File.separator + "valid");
        File[] sourceFiles = validFolder.listFiles();
        for (File f : sourceFiles) {
            if (f.isFile() && f.getName().endsWith(".m")) {
                data.add(new Object[] { f , f.getName()});
            }
        }
        return data;
    }
}

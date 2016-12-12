package components.helpers;

import java.io.File;

public class Position {
    public final int line;
    public final File path;
    
    public Position(File path, int line) {
        this.path = path;
        this.line = line;
    }
}
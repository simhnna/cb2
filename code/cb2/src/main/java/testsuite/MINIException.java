package testsuite;

import java.io.File;

/**
 * Top level expected exception thrown by a MINI compiler.
 * 
 * @author Timm Felden
 */
public abstract class MINIException extends Exception {
    private final File file;
    private final int line;
    private final String kind;

    protected MINIException(String message, File file, int line, String kind) {
        super(message);
        this.file = file;
        this.line = line;
        this.kind = kind;
    }

    File getFile() {
        return file;
    }

    int getLineNumber() {
        return line;
    }

    /**
     * @return either "typecheck" or "wortproblem"
     */
    String getKind() {
        return kind;
    }

}

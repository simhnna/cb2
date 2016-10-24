package testsuite;

import java.io.File;

/**
 * This exception shall be used to indicate semantic errors such as duplicate
 * definitions or type mismatch.
 * 
 * @author Timm Felden
 */
public class TypeException extends MINIException {

    public TypeException(File file, int line, String problem) {
        super(String.format("Type Error in %s\n\tline %d: %s", file.getPath(), line, problem), file, line, "typecheck");
    }

    public TypeException(File file, int line, String problemFormat, Object... args) {
        super(String.format("Type Error in %s\n\tline %d: %s", file.getPath(), line,
                String.format(problemFormat, args)), file, line, "typecheck");
    }

}

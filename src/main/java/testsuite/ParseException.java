package testsuite;

import java.io.File;

/**
 * This exception shall be used to indicate syntactic errors, such as missing
 * ';'
 * 
 * @author Timm Felden
 */
public class ParseException extends MINIException {

    public ParseException(File file, int line, String problem) {
        super(String.format("Syntactic Error in %s\n\tline %d: %s", file.getPath(), line, problem), file, line,
                "wortproblem");
    }

    public ParseException(File file, int line, String problemFormat, Object... args) {
        super(String.format("Syntactic Error in %s\n\tline %d: %s", file.getPath(), line,
                String.format(problemFormat, args)), file, line, "wortproblem");
    }

}

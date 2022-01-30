package errors;
/**
 *
 * @author elsho
 */
public class CompilationException extends CrotoException {


    public CompilationException(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return "Compilation Error: Line " + line + ", column " + column + ".";
    }
}
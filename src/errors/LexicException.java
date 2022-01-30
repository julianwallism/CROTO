package errors;
/**
 *
 * @author elsho
 */
public class LexicException extends CrotoException {

    private String message;

    public LexicException(int line, int column, String message) {
        super(line, column);
        this.message = message;
    }

    @Override
    public String toString() {
        return "Lexic Error: Line " + line + ", column " + column + ". " + message;
    }
}
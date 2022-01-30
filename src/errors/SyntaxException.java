package errors;
/**
 *
 * @author elsho
 */
public class SyntaxException extends CrotoException {

    private String expectedTokens;
    private Object obj;

    public SyntaxException(int line, int column, Object obj, String expectedTokens) {
        super(line, column);
        this.expectedTokens = expectedTokens; 
        this.obj = obj;
    }

    @Override
    public String toString() {
        String error = "Syntax Error: Line " + line + ", column " + column + ".";
        if(!obj.equals("")){
            error += " At " + obj + expectedTokens + ".";
        }
        return error;
    }
}
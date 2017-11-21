package easy.devils.exception;

/**
 * @author limengyu
 * @create 2017/11/21
 */
public abstract class AbstractDevilsException extends RuntimeException{

    private DevilsErrorMessage errorMessage = DevilsExceptionConstant.FRAMEWORK_FEFAULT_ERROR;

    private String message;


    public AbstractDevilsException() {
        super();
    }

    public AbstractDevilsException(String message) {
        super(message);
    }

    public AbstractDevilsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractDevilsException(Throwable cause) {
        super(cause);
    }

    protected AbstractDevilsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}

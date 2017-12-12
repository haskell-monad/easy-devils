package easy.devils.exception;

/**
 * @author limengyu
 * @create 2017/11/22
 */
public class DevilsFrameworkException extends DevilsAbstractException {
    public DevilsFrameworkException() {
        super(DevilsExceptionConstant.FRAMEWORK_DEFAULT_ERROR);
    }

    public DevilsFrameworkException(String message, Throwable cause, DevilsErrorMessage devilsErrorMessage) {
        super(message, cause, devilsErrorMessage);
    }

    public DevilsFrameworkException(Throwable cause, DevilsErrorMessage devilsErrorMessage) {
        super(cause, devilsErrorMessage);
    }

    public DevilsFrameworkException(String message, DevilsErrorMessage devilsErrorMessage) {
        super(message, devilsErrorMessage);
    }
    public DevilsFrameworkException(DevilsErrorMessage devilsErrorMessage) {
        super(devilsErrorMessage);
    }

    public DevilsFrameworkException(String message) {
        super(message,DevilsExceptionConstant.FRAMEWORK_DEFAULT_ERROR);
    }

    public DevilsFrameworkException(String message, Throwable cause) {
        super(message, cause,DevilsExceptionConstant.FRAMEWORK_DEFAULT_ERROR);
    }

    public DevilsFrameworkException(Throwable cause) {
        super(cause,DevilsExceptionConstant.FRAMEWORK_DEFAULT_ERROR);
    }
}

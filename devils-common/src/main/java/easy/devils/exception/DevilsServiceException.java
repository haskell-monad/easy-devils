package easy.devils.exception;

/**
 * @author limengyu
 * @create 2017/11/22
 */
public class DevilsServiceException extends DevilsAbstractException {
    public DevilsServiceException() {
        super(DevilsExceptionConstant.SERVICE_DEFAULT_ERROR);
    }

    public DevilsServiceException(String message, Throwable cause, DevilsErrorMessage devilsErrorMessage) {
        super(message, cause, devilsErrorMessage);
    }

    public DevilsServiceException(Throwable cause, DevilsErrorMessage devilsErrorMessage) {
        super(cause, devilsErrorMessage);
    }

    public DevilsServiceException(String message) {
        super(message,DevilsExceptionConstant.SERVICE_DEFAULT_ERROR);
    }

    public DevilsServiceException(String message, Throwable cause) {
        super(message, cause,DevilsExceptionConstant.SERVICE_DEFAULT_ERROR);
    }

    public DevilsServiceException(Throwable cause) {
        super(cause,DevilsExceptionConstant.SERVICE_DEFAULT_ERROR);
    }
}

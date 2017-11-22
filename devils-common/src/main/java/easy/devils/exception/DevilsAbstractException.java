package easy.devils.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @author limengyu
 * @create 2017/11/21
 */
public abstract class DevilsAbstractException extends RuntimeException{

    private DevilsErrorMessage devilsErrorMessage = DevilsExceptionConstant.FRAMEWORK_DEFAULT_ERROR;

    private String errorMsg;

    public DevilsAbstractException() {
        super();
    }

    public DevilsAbstractException(DevilsErrorMessage devilsErrorMessage) {
        super();
        this.devilsErrorMessage = devilsErrorMessage;
    }
    public DevilsAbstractException(DevilsErrorMessage devilsErrorMessage,Throwable cause) {
        super(cause);
        this.devilsErrorMessage = devilsErrorMessage;
    }
    public DevilsAbstractException(String message,Throwable cause,DevilsErrorMessage devilsErrorMessage) {
        super(cause);
        this.errorMsg = message;
        this.devilsErrorMessage = devilsErrorMessage;
    }
    public DevilsAbstractException(Throwable cause,DevilsErrorMessage devilsErrorMessage) {
        super(cause);
        this.devilsErrorMessage = devilsErrorMessage;
    }
    public DevilsAbstractException(String message,DevilsErrorMessage devilsErrorMessage) {
        super(message);
        this.errorMsg = message;
        this.devilsErrorMessage = devilsErrorMessage;
    }

    public DevilsAbstractException(String message) {
        super(message);
        this.errorMsg = message;
    }

    public DevilsAbstractException(String message, Throwable cause) {
        super(message, cause);
        this.errorMsg = message;
    }

    public DevilsAbstractException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        if(devilsErrorMessage == null){
            return super.getMessage();
        }
        String msg;
        if(StringUtils.isNotBlank(errorMsg)){
            msg = errorMsg;
        }else{
            msg = devilsErrorMessage.getMessage();
        }
        return "errorStatus: "+devilsErrorMessage.getStatus()+",errorMsg: "+msg+ ",errorCode: "+devilsErrorMessage.getErrorCode();
    }

}

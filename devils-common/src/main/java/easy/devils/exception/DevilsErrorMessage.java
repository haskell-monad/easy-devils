package easy.devils.exception;

import java.io.Serializable;

/**
 * @author limengyu
 * @create 2017/11/21
 */
public class DevilsErrorMessage implements Serializable {
    private static final long serialVersionUID = 8586493002165776282L;
    private int status;
    private int errorCode;
    private String message;

    public DevilsErrorMessage() {
    }

    public DevilsErrorMessage(int status,int errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

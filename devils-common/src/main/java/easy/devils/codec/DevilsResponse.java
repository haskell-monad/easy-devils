package easy.devils.codec;

import java.io.Serializable;

/**
 * @author limengyu
 * @create 2017/11/18
 */
public class DevilsResponse implements Serializable {
    private static final long serialVersionUID = 2572583623720933595L;
    private int code = 0;
    private String message;
    private Object result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DevilsResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}

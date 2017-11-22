package easy.devils.exception;

/**
 * @author limengyu
 * @create 2017/11/21
 */
public class DevilsExceptionConstant {
    /**
     * 503
     */
    public static final int SERVICE_DEFAULT_ERROR_CODE = 10001;
    public static final int SERVICE_TIMEOUT_ERROR_CODE = 10002;
    /**
     * 404
     */
    public static final int SERVICE_NOT_FOUND_ERROR_CODE = 10010;
    /**
     * 403
     */
    public static final int SERVICE_REQUEST_LENGTH_LIMIT_ERROR_CODE = 10020;
    /**
     * 503 framework error
     */
    public static final int FRAMEWORK_DEFAULT_ERROR_CODE = 20001;
    public static final int FRAMEWORK_ENCODE_ERROR_CODE = 20002;
    public static final int FRAMEWORK_DECODE_ERROR_CODE = 20003;
    public static final int FRAMEWORK_INIT_ERROR_CODE = 20004;

    /**
     * 框架默认错误
     */
    public static final DevilsErrorMessage FRAMEWORK_DEFAULT_ERROR =
            new DevilsErrorMessage(500,FRAMEWORK_DEFAULT_ERROR_CODE,"framework default error");

    public static final DevilsErrorMessage FRAMEWORK_ENCODE_ERROR =
            new DevilsErrorMessage(503,FRAMEWORK_ENCODE_ERROR_CODE,"framework encode error");

    public static final DevilsErrorMessage FRAMEWORK_DECODE_ERROR =
            new DevilsErrorMessage(503,FRAMEWORK_DECODE_ERROR_CODE,"framework decode error");

    public static final DevilsErrorMessage FRAMEWORK_INIT_ERROR =
            new DevilsErrorMessage(503,FRAMEWORK_INIT_ERROR_CODE,"framework init error");


    /**
     * 服务默认错误
     */
    public static final DevilsErrorMessage SERVICE_DEFAULT_ERROR =
            new DevilsErrorMessage(503,SERVICE_DEFAULT_ERROR_CODE,
                    "service default error");

    public static final DevilsErrorMessage SERVICE_TIMEOUT_ERROR =
            new DevilsErrorMessage(503,SERVICE_TIMEOUT_ERROR_CODE,"service request timeout error");

    public static final DevilsErrorMessage SERVICE_NOT_FOUND_ERROR =
            new DevilsErrorMessage(404,SERVICE_NOT_FOUND_ERROR_CODE,"service not found error");

    public static final DevilsErrorMessage SERVICE_REQUEST_LENGTH_LIMIT_ERROR =
            new DevilsErrorMessage(403,SERVICE_REQUEST_LENGTH_LIMIT_ERROR_CODE,"service request length limit error");


}

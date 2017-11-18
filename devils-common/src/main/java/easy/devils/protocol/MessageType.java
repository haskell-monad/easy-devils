package easy.devils.protocol;

import easy.devils.codec.DevilsRequest;
import easy.devils.codec.DevilsResponse;

/**
 * @author limengyu
 * @create 2017/11/18
 */
public enum MessageType {

	REQUEST((byte) 0),
    RESPONSE((byte) 1);

    private byte value;
    private static final byte RESPONSE_TYPE_MESSAGE = (byte)(1 << 7);

	MessageType(byte value) {
		this.value = value;
	}
	public byte getValue() {
		return value;
	}

    public static Class getMessageTypeByExtend(byte value){
        switch (value & RESPONSE_TYPE_MESSAGE){
            case 0x0:
                return DevilsRequest.class;
            case RESPONSE_TYPE_MESSAGE:
                return DevilsResponse.class;
            default:
                return DevilsRequest.class;
        }
    }
}

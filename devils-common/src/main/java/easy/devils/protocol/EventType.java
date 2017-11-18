package easy.devils.protocol;

/**
 * @author limengyu
 */
public enum EventType {

    NORMAL((byte)0),
    HEART_BEAT((byte)(1 << 5)),
    EXCEPTION((byte)(1 << 6));

    private byte value;

    EventType(byte value){
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static boolean isHeartbeat(byte value){
        return (value & HEART_BEAT.getValue()) == HEART_BEAT.getValue() ? true : false;
    }

    public static boolean isException(byte value){
        return (value & EXCEPTION.getValue()) == EXCEPTION.getValue() ? true : false;
    }
}

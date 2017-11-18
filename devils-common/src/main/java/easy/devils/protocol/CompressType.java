package easy.devils.protocol;

import easy.devils.codec.compress.ICompress;
import easy.devils.codec.compress.impl.GzipCompress;
import easy.devils.codec.compress.impl.NoneCompress;
import easy.devils.codec.compress.impl.SnappyCompress;

/**
 * @author limengyu
 */
public enum CompressType {

    NONE((byte)0),
    /**
     * 01000
     * 11000
     */
    GZIP((byte)(1 << 3)),
    /**
     * 10000
     * 11000
     */
    SNAPPY((byte)(1 << 4));

    private byte value;
    public static final CompressType DEFAULT_COMPRESS = NONE;

    CompressType(byte value){
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static ICompress getCompressTypeByExtend(byte value){
        switch (value & 24){
            case (0x0):
                return new NoneCompress();
            case (1 << 3):
                return new GzipCompress();
            case (1 << 4):
                return new SnappyCompress();
            default:
                return new NoneCompress();
        }
    }
}

package easy.devils.codec.compress.impl;

import easy.devils.codec.compress.ICompress;

/**
 * @author limengyu
 * @create 2017/11/18
 */
public class NoneCompress implements ICompress {
    @Override
    public byte[] compress(byte[] array) {
        return array;
    }

    @Override
    public byte[] uncompress(byte[] array) {
        return array;
    }
}

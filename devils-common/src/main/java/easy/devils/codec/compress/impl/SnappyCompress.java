package easy.devils.codec.compress.impl;

import easy.devils.codec.compress.ICompress;
import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * 适合大量数据流动
 * 不适合永久存储
 * @author limengyu
 * @create 2017/11/18
 */
public class SnappyCompress implements ICompress {
    @Override
    public byte[] compress(byte[] array) throws IOException {
        return array == null ? null : Snappy.compress(array);
    }

    @Override
    public byte[] uncompress(byte[] array) throws IOException {
        return array == null ? null : Snappy.uncompress(array);
    }
}

package easy.devils.codec.compress;

import java.io.IOException;

/**
 * @author limengyu
 */
public interface ICompress {

    /**
     * 压缩
     * @param array
     * @return
     */
    byte[] compress(byte[] array) throws IOException;

    /**
     * 解压缩
     * @param array
     * @return
     */
    byte[] uncompress(byte[] array) throws IOException;
}

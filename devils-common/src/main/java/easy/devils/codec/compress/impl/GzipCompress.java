package easy.devils.codec.compress.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import easy.devils.codec.compress.ICompress;

/**
 * @author limengyu
 * @create 2017/11/18
 */
public class GzipCompress implements ICompress {
	@Override
	public byte[] compress(byte[] array) throws IOException {
		if (array == null) {
			return array;
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		GZIPOutputStream gzipOutputStream = null;
		try {
			gzipOutputStream = new GZIPOutputStream(outputStream);
			gzipOutputStream.write(array);
			gzipOutputStream.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			if (gzipOutputStream != null) {
				gzipOutputStream.close();
			}
		}
		return outputStream.toByteArray();
	}
	@Override
	public byte[] uncompress(byte[] array) throws IOException {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(array);
		GZIPInputStream gzipInputStream = new GZIPInputStream(arrayInputStream);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gzipInputStream.read(buffer)) > 0) {
			arrayOutputStream.write(buffer, 0, n);
		}
		return arrayOutputStream.toByteArray();
	}
}

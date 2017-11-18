package easy.devils.codec.serialize.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.FastInput;
import com.esotericsoftware.kryo.io.FastOutput;
import com.esotericsoftware.kryo.io.KryoObjectInput;
import com.esotericsoftware.kryo.io.KryoObjectOutput;

import easy.devils.codec.serialize.ISerialize;

/**
 * Hessian 序列化
 * @author limengyu
 * @create 2017/11/8
 */
public class KryoSerializeImpl implements ISerialize {
	private static final ThreadLocal<Kryo> THREAD_LOCAL = new ThreadLocal<>();

	public KryoSerializeImpl() {
		THREAD_LOCAL.set(new Kryo());
	}
	@Override
	public <T> byte[] serialize(T obj) throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Kryo kryo = THREAD_LOCAL.get();
		KryoObjectOutput output = new KryoObjectOutput(kryo, new FastOutput(stream));
		output.writeObject(obj);
		output.flush();
		return stream.toByteArray();
	}
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
		Kryo kryo = THREAD_LOCAL.get();
		kryo.register(clazz);
		KryoObjectInput objectInput = new KryoObjectInput(kryo, new FastInput(new ByteArrayInputStream(bytes)));
		return (T) objectInput.readObject();
	}
}

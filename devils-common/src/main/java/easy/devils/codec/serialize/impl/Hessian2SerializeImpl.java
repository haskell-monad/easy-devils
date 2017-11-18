package easy.devils.codec.serialize.impl;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import easy.devils.codec.serialize.ISerialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Hessian 序列化
 * @author limengyu
 * @create 2017/11/8
 */
public class Hessian2SerializeImpl implements ISerialize {
    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        Hessian2Output hessian2Output = new Hessian2Output(arrayOutputStream);
        hessian2Output.writeObject(obj);
        hessian2Output.flush();
        return arrayOutputStream.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        Hessian2Input hessian2Input = new Hessian2Input(new ByteArrayInputStream(bytes));
        return (T)hessian2Input.readObject(clazz);
    }
}

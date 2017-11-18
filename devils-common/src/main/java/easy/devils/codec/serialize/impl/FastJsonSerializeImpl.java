package easy.devils.codec.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import easy.devils.codec.serialize.ISerialize;
import easy.devils.common.DevilsConstant;

import java.io.IOException;

/**
 * jackson 序列化
 * @author limengyu
 * @create 2017/11/8
 */
public class FastJsonSerializeImpl implements ISerialize {
    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        SerializeWriter serializeWriter = new SerializeWriter();
        JSONSerializer jsonSerializer = new JSONSerializer(serializeWriter);
        jsonSerializer.config(SerializerFeature.WriteEnumUsingToString,true);
        jsonSerializer.config(SerializerFeature.WriteClassName, true);
        jsonSerializer.write(obj);
        return serializeWriter.toBytes(DevilsConstant.DEFAULT_CHARSET);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        return JSON.parseObject(bytes,clazz);
    }
}

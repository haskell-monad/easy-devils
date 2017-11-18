import easy.devils.codec.DevilsRequest;
import easy.devils.codec.serialize.ISerialize;
import easy.devils.codec.serialize.impl.FastJsonSerializeImpl;
import easy.devils.codec.serialize.impl.JavaSerializeImpl;
import easy.devils.codec.serialize.impl.ProtoStuffSerializeImpl;

/**
 * @author limengyu
 * @create 2017/11/18
 */
public class SerializeTest {

    public static ISerialize builderSerialize(){
        ISerialize serialize = new ProtoStuffSerializeImpl();
        return serialize;
    }

    public static void main(String[] args) throws Exception {

        ISerialize serialize = builderSerialize();

        DevilsRequest devilsRequest = new DevilsRequest();
        devilsRequest.setServiceName("test");
        devilsRequest.setMethodName("hell");
        devilsRequest.setParams(new Object[]{"test", 1});

        DevilsRequest request = serialize.deserialize(serialize.serialize(devilsRequest), DevilsRequest.class);
        System.out.println(request);
    }
}

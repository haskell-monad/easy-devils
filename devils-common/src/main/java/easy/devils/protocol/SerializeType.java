package easy.devils.protocol;

import easy.devils.codec.serialize.ISerialize;
import easy.devils.codec.serialize.impl.AvroSerializeImpl;
import easy.devils.codec.serialize.impl.FastJsonSerializeImpl;
import easy.devils.codec.serialize.impl.Hessian2SerializeImpl;
import easy.devils.codec.serialize.impl.JavaSerializeImpl;
import easy.devils.codec.serialize.impl.KryoSerializeImpl;
import easy.devils.codec.serialize.impl.MarshallingSerializeImpl;
import easy.devils.codec.serialize.impl.ProtoStuffSerializeImpl;
import easy.devils.codec.serialize.impl.ThriftSerializeImpl;

/**
 * @author limengyu
 */
public enum SerializeType {

    JAVA("Java",(byte)0),
    HESSIAN2("Hessian2",(byte)1),
    FastJson("FastJson",(byte)2),
    Kryo("Kryo",(byte)3),
    Marshall("Marshall",(byte)4),
    ProtoBuf("ProtoBuf",(byte)5),
    Thrift("Thrift",(byte)6),
    Avro("Avro",(byte)7);

    public static final SerializeType DEFAULT_SERIALIZE = JAVA;

    SerializeType(String name,byte value){
        this.name = name;
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    private String name;
    private byte value;

    private final static SerializeType DEFAULT_TYPE = JAVA;

    public static SerializeType getSerializeTypeByName(String name){
        SerializeType[] values = SerializeType.values();
        for (SerializeType value : values) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return DEFAULT_TYPE;
    }

    public static ISerialize getSerializeTypeByExtend(byte value){
        switch (value & 0x7){
            case (0x0):
                return new JavaSerializeImpl();
            case (0x1):
                return new Hessian2SerializeImpl();
            case (0x2):
                return new FastJsonSerializeImpl();
            case (0x3):
                return new KryoSerializeImpl();
            case (0x4):
                return new MarshallingSerializeImpl();
            case (0x5):
                return new ProtoStuffSerializeImpl();
            case (0x6):
                return new ThriftSerializeImpl();
            case (0x7):
                return new AvroSerializeImpl();
            default:
                return new JavaSerializeImpl();
        }
    }

    public static void main(String[] args) {
        System.out.println(1 << 0);
        System.out.println(1 << 1);
        System.out.println(1 << 2);
        System.out.println(1 << 3);
        System.out.println(1 << 4);
        System.out.println(1 << 5);
        System.out.println(1 << 6);
        System.out.println("===============");
        System.out.println(7 | 0);
        System.out.println(7 | 8);
        System.out.println(7 | 16);
        System.out.println(23 & 24);
    }
}

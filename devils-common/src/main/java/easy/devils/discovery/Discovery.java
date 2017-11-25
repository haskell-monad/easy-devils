package easy.devils.discovery;

import easy.devils.discovery.impl.LocalServiceDiscovery;
import easy.devils.discovery.impl.ZkServiceDiscovery;
import org.apache.commons.lang3.StringUtils;

/**
 * @author limengyu
 */
public enum Discovery {

	LOCAL("local", LocalServiceDiscovery.class),
    ZOOKEEPER("zookeeper", ZkServiceDiscovery.class);

    private String protocol;
	private Class<? extends AbstractServiceDiscovery> classType;

	Discovery(String protocol, Class<? extends AbstractServiceDiscovery> clazzType) {
		this.protocol = protocol;
		this.classType = clazzType;
	}

    /**
     * 通过名称标识获取具体的类型
     * @param protocol
     * @return
     */
    public static Class<?> getClassTypeByProtocol(String protocol){
        if(ZOOKEEPER.protocol.equals(protocol)){
            return ZOOKEEPER.classType;
        }else{
            return LOCAL.classType;
        }
    }

    public static void main(String[] args) {
        System.out.println(getClassTypeByProtocol("local"));
    }
}

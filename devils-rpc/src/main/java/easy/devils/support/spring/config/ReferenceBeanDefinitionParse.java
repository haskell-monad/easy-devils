package easy.devils.support.spring.config;

import com.google.common.collect.Maps;
import easy.devils.config.MethodProviderConfig;
import easy.devils.discovery.impl.LocalServiceDiscovery;
import easy.devils.exception.DevilsExceptionConstant;
import easy.devils.exception.DevilsFrameworkException;
import easy.devils.protocol.CompressType;
import easy.devils.protocol.SerializeType;
import easy.devils.support.spring.DevilsProxyFactoryBean;
import easy.devils.support.spring.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;
import java.util.Map;

/**
 * 生成代理工厂DevilsProxyFactoryBean.class
 * 解析客户端引用的服务接口bean
 * 并生成相应服务接口类的代理类
 * @author limengyu
 * @create 2017/11/23
 */
public class ReferenceBeanDefinitionParse implements BeanDefinitionParser{

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        String serviceBean = element.getAttribute("interface");
        Class<?> serviceInterface;
        try {
            serviceInterface = Class.forName(serviceBean);
        } catch (ClassNotFoundException e) {
            throw new DevilsFrameworkException(e.getMessage(), DevilsExceptionConstant.SERVICE_NOT_FOUND_ERROR);
        }
        //DevilsProxyFactoryBean.class
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DevilsProxyFactoryBean.class);
        //读取xml配置文件里面的服务接口配置
        //服务类
        builder.addPropertyValue("serviceInterface",serviceInterface);
        //服务接口信息
        Map<String,MethodProviderConfig> exportServiceMap = Maps.newHashMap();
        List<Element> methodList = DomUtils.getChildElementsByTagName(element, "method");
        for (Element method : methodList) {
            MethodProviderConfig config = MethodProviderConfig.Builder
                    .createBuilder()
                    .setServiceName(SpringUtils.getBeanId(null, serviceInterface))
                    .setMethodName(method.getAttribute("name"))
                    .setCompress(CompressType.getCompressTypeByName(method.getAttribute("compressType")))
                    .setSerialize(SerializeType.getSerializeTypeByName(method.getAttribute("serializeType")))
                    .setTimeOut(StringUtils.isBlank(method.getAttribute("timeout")) ? 0 : Long.parseLong(method.getAttribute("timeout")))
                    .build();
            exportServiceMap.put(config.getMethodName(),config);
        }
        builder.addPropertyValue("methodProviderConfigMap",exportServiceMap);
        //注册中心
        String registry = element.getAttribute("registry");
        if(StringUtils.isNotBlank(registry)){
            builder.addPropertyValue("registry",new LocalServiceDiscovery<>());
        }else{
            builder.addPropertyReference("registry",registry);
        }
        //定义接口类的代理类
        String beanId = SpringUtils.getBeanId(element,serviceInterface);
        parserContext.getRegistry().registerBeanDefinition(beanId,builder.getBeanDefinition());
        return null;
    }
}

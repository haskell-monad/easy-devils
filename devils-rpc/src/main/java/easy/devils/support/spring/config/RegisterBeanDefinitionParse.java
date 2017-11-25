package easy.devils.support.spring.config;

import easy.devils.discovery.Discovery;
import easy.devils.support.spring.SpringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * AbstractServiceDiscovery
 * 服务注册中心bean定义
 * @author limengyu
 * @create 2017/11/23
 */
public class RegisterBeanDefinitionParse implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        //获取属性值
        String name = element.getAttribute("name");
        String address = element.getAttribute("address");
        String protocol = element.getAttribute("protocol");

        Class<?> registerClass = Discovery.getClassTypeByProtocol(protocol);
        String beanId = SpringUtils.getBeanId(element,registerClass);
        //构建bean
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(registerClass);
        builder.addPropertyValue("address",address);
        builder.addPropertyValue("name",name);
        builder.setInitMethodName("start");
        //注册bean
        parserContext.getRegistry().registerBeanDefinition(beanId,builder.getBeanDefinition());
        return null;
    }
}

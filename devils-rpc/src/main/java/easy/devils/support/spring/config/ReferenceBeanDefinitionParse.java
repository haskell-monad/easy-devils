package easy.devils.support.spring.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 生成代理工厂DevilsProxyFactoryBean
 * 解析客户端引用的服务接口bean
 * 并生成相应服务接口的代理类
 * @author limengyu
 * @create 2017/11/23
 */
public class ReferenceBeanDefinitionParse implements BeanDefinitionParser{

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return null;
    }
}

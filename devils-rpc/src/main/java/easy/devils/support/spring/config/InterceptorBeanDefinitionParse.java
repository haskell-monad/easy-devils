package easy.devils.support.spring.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 拦截器bean定义
 * @author limengyu
 * @create 2017/11/23
 */
public class InterceptorBeanDefinitionParse implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return null;
    }
}

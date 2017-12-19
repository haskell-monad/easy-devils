package easy.devils.support.spring.config;

import easy.devils.exception.DevilsErrorMessage;
import easy.devils.exception.DevilsExceptionConstant;
import easy.devils.exception.DevilsFrameworkException;
import easy.devils.support.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;

/**
 * 拦截器bean定义
 * @author limengyu
 * @create 2017/11/23
 */
public class InterceptorBeanDefinitionParse implements BeanDefinitionParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterceptorBeanDefinitionParse.class);

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        List<Element> interceptorList = DomUtils.getChildElementsByTagName(element, "interceptor");
        if(interceptorList != null && interceptorList.size() > 0){
            for (Element interceptor : interceptorList) {
                String className = interceptor.getAttribute("class");
                try {
                    Class<?> aClass = Class.forName(className);
                    BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(aClass);
                    String beanId = SpringUtils.getBeanId(element,aClass);
                    parserContext.getRegistry().registerBeanDefinition(beanId,beanDefinition.getBeanDefinition());
                } catch (ClassNotFoundException e) {
                    LOGGER.error(e.getMessage(),e);
                    throw new DevilsFrameworkException("Interceptor class "+className+" not found");
                }
            }
        }
        return null;
    }
}

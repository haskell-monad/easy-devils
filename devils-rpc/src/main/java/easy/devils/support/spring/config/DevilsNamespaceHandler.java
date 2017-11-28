package easy.devils.support.spring.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author limengyu
 * @create 2017/11/22
 */
public class DevilsNamespaceHandler extends NamespaceHandlerSupport{

    @Override
    public void init() {
        registerBeanDefinitionParser("registry",new RegisterBeanDefinitionParse());
        registerBeanDefinitionParser("server",new ServerBootstrapBeanDefinitionParse());
        registerBeanDefinitionParser("interceptors",new InterceptorBeanDefinitionParse());
        registerBeanDefinitionParser("reference",new ReferenceBeanDefinitionParse());
    }
}

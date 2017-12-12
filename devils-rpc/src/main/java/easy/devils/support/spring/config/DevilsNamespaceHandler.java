package easy.devils.support.spring.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author limengyu
 * @create 2017/11/22
 */
public class DevilsNamespaceHandler extends NamespaceHandlerSupport{

    @Override
    public void init() {
        registerBeanDefinitionParser("interceptors",new InterceptorBeanDefinitionParse());
        registerBeanDefinitionParser("registry",new RegisterBeanDefinitionParse());
        registerBeanDefinitionParser("reference",new ReferenceBeanDefinitionParse());
        registerBeanDefinitionParser("server",new ServerBootstrapBeanDefinitionParse());
    }
}

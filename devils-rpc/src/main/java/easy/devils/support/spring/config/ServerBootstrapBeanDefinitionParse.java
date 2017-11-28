package easy.devils.support.spring.config;

import easy.devils.support.spring.SpringUtils;
import easy.devils.transport.DevilsServer;
import easy.devils.transport.ServerBootstrap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Element;

/**
 * Server-bean定义,注入ServerBootstrap
 * 启动DevilsServer、HttpServer
 * @author limengyu
 * @create 2017/11/23
 */
public class ServerBootstrapBeanDefinitionParse implements BeanDefinitionParser{

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        String beanId = SpringUtils.getBeanId(element, ServerBootstrap.class);
        String registry = element.getAttribute("registry");
        String host = element.getAttribute("host");
        String startHttpServer = element.getAttribute("startHttpServer");

        ApplicationContext applicationContext = (ApplicationContext)parserContext.getReaderContext().getReader().getResourceLoader();


        //注册ServerBootstrap
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ServerBootstrap.class);
        builder.addPropertyValue("startHttpServer", StringUtils.isBlank(startHttpServer) ? false : Boolean.parseBoolean(startHttpServer));
        builder.addPropertyValue("applicationContext",applicationContext);
        builder.addPropertyReference("serviceDiscovery",registry);
        builder.setInitMethodName("start");

        parserContext.getRegistry().registerBeanDefinition(beanId,builder.getBeanDefinition());
        return null;
    }
}

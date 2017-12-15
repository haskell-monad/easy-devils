package easy.devils.demo.client;

import easy.devils.demo.api.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.stream.IntStream;

/**
 * @author limengyu
 * @create 2017/12/14
 */
public class NettyClientTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-client.xml");

        for (int i = 0;i < 100;i++){
            HelloService helloService = (HelloService)context.getBean("helloService");
            helloService.sayHello("world_" + i);
        }
    }
}

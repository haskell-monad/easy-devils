package easy.devils.demo.server;

import easy.devils.annotation.MethodExport;
import easy.devils.annotation.ServiceExport;
import easy.devils.demo.api.HelloService;

/**
 * @author limengyu
 * @create 2017/11/18
 */
@ServiceExport
public class HelloServiceImpl implements HelloService{

    @MethodExport
    @Override
    public String sayHello(String name) {
        System.out.println("hello "+name);
        return "hello "+name;
    }

    @MethodExport
    @Override
    public String whois(String name) {
        return "I'am "+name;
    }
}

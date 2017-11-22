package easy.devils.demo.server;

import easy.devils.demo.api.HelloService;

/**
 * @author limengyu
 * @create 2017/11/18
 */
public class HelloServiceImpl implements HelloService{

    @Override
    public void sayHello(String name) {
        System.out.println("hello "+name);
    }

    @Override
    public String whois(String name) {
        return "I'am "+name;
    }
}

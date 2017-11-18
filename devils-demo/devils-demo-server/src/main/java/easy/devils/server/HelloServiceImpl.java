package easy.devils.server;

import easy.devils.api.HelloService;

/**
 * @author limengyu
 * @create 2017/11/18
 */
public class HelloServiceImpl implements HelloService{

    @Override
    public void sayHello(String name) {
        System.out.println("hello "+name);
    }
}

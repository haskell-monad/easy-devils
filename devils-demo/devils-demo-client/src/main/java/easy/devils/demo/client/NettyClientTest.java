package easy.devils.demo.client;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import easy.devils.Devils;
import easy.devils.demo.api.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author limengyu
 * @create 2017/12/14
 */
public class NettyClientTest {

    public static void testSpring(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-client.xml");
        HelloService helloService = (HelloService)context.getBean("helloService");
        IntStream.range(0,100).forEach(i -> helloService.sayHello("world_" + i));
    }

    public static void metricTest(){
        HelloService helloService = Devils.newProxyInstance(HelloService.class);
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("metricTest-Thread")
                .build();
        ExecutorService service = new ThreadPoolExecutor(20,20,1, TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(1024),
                threadFactory,new ThreadPoolExecutor.AbortPolicy());
        IntStream.range(0, 20).forEach(i ->
            service.submit(() ->
                IntStream.range(0,1000000000).forEach(n -> {
                        String s = helloService.sayHello("world_" + n);
                        if (n % 100000 == 0) {
                            System.out.println(s);
                        }
                })
            )
        );
//        for (int i = 0; i < 20;i++){
//            service.submit(() -> {
//                        for (int n = 0; n < 1000000000; n++) {
//                            String s = helloService.sayHello("world_" + n);
//                            if (n % 100000 == 0) {
//                                System.out.println(s);
//                            }
//                        }
//                    }
//            );
//        }
    }

    public static void main(String[] args) {
//        testSpring();
        metricTest();
    }
}

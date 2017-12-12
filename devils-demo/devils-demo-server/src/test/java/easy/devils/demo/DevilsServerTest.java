package easy.devils.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author limengyu
 * @create 2017/12/12
 */
public class DevilsServerTest {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext(new String[]{"application.xml"});
    }
}

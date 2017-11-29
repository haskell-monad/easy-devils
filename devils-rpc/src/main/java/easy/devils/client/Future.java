package easy.devils.client;

import java.util.concurrent.TimeUnit;

/**
 * @author limengyu
 */
public interface Future<T> {

    T await() throws Exception;

    T await(long timeout,TimeUnit timeUnit) throws Exception;
}

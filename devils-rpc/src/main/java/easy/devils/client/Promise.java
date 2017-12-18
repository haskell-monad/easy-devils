package easy.devils.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author limengyu
 */
public class Promise<T> implements Callback<T>,Future<T>{

    private final CountDownLatch latch = new CountDownLatch(1);

    private T message;

    @Override
    public void onReceive(T message) {
        synchronized (this){
            this.message = message;
            latch.countDown();
        }
    }

    @Override
    public T await() throws Exception {
        latch.await();
        return getResult();
    }

    @Override
    public T await(long timeout, TimeUnit timeUnit) throws Exception {
        if(latch.await(timeout, timeUnit)){
            return getResult();
        }else{
            //如果等待时间在计数到达0之前经过
            throw new TimeoutException();
        }
    }

    public T getResult(){
        return message;
    }
}

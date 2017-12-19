package easy.devils.support.interceptor;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import easy.devils.DevilsContext;
import easy.devils.codec.DevilsRequest;
import easy.devils.interceptor.AbstractInterceptor;
import easy.devils.utils.DevilsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 度量拦截器
 * @author limengyu
 * @create 2017/12/19
 */
public class MetricInterceptor extends AbstractInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricInterceptor.class);

    /**
     * <serviceName,Metric>
     */
    private static final Map<String,Metric> METHOD_METRIC = Maps.newConcurrentMap();

    private static final String START_TIME = "st";

    static{
		ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("Metric-Info")
                .build();
		new ScheduledThreadPoolExecutor(1, threadFactory)
                .scheduleAtFixedRate(() -> METHOD_METRIC.forEach((serviceName, metric) -> {
                    Metric tempMetric = metric.init();
                    if(tempMetric.currentTps > 37000){
                        LOGGER.info("serviceName:[{}],current-TPS:[{}],totalTime:[{}],maxTime:[{}],minTime:[{}],meanTime:[{}]", serviceName, tempMetric.currentTps, tempMetric.totalTime, tempMetric.maxTime, tempMetric.minTime, tempMetric.totalTime / tempMetric.currentTps);
                    }
                }), 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public boolean beforeInvoker(Object target, Method method, Object... params) {
        DevilsContext.setAttributeValue(START_TIME,String.valueOf(System.currentTimeMillis()));
        return true;
    }

    @Override
    public boolean afterInvoker(Object target, Method method, Object... params) {
        Object content = DevilsContext.getDevilsMessage().getContent();
        if(!(content instanceof DevilsRequest)){
            return true;
        }
        long currentTime = System.currentTimeMillis();
        long execTime = currentTime - Long.parseLong(DevilsContext.getAttributeValue(START_TIME));
        DevilsRequest request = (DevilsRequest)content;
        String methodName = DevilsUtils.builderRoutingUri(request.getServiceName(), request.getMethodName());
        Metric metric = METHOD_METRIC.get(methodName);
        if(metric == null){
            synchronized (this){
                metric = METHOD_METRIC.get(methodName);
                if(metric == null){
                    metric = new Metric();
                    METHOD_METRIC.put(methodName,metric);
                }
            }
        }
        metric.updateTps();
        metric.updateExecTime(execTime);
        return true;
    }

    class Metric{
        private long minTime;
        private long maxTime;
        private long totalTime;
        private long currentTps;
        private AtomicLong tps = new AtomicLong(0);

        public Metric() {
        }

        public Metric(long minTime, long maxTime, long totalTime,long currentTps) {
            this.minTime = minTime;
            this.maxTime = maxTime;
            this.totalTime = totalTime;
            this.currentTps = currentTps;
        }

        /**
         * 初始化度量
         * @return
         */
        public Metric init(){
            Metric metric = new Metric(minTime, maxTime, totalTime, tps.getAndSet(0));
            totalTime = 0;
            return metric;
        }

        /**
         * 更新tps
         * @return
         */
        public long updateTps(){
            return tps.incrementAndGet();
        }

        /**
         * 更新执行时间
         */
        public synchronized void updateExecTime(long execTime){
            if(minTime > execTime || minTime == 0){
                minTime = execTime;
            }else if(maxTime < execTime){
                maxTime = execTime;
            }
            totalTime += execTime;
        }
    }
}

package easy.devils.processor;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author limengyu
 * @create 2017/11/25
 */
public abstract class AbstractAnnotationProcessorProvider {

    public static final AbstractAnnotationProcessorProvider DEFAULT_PROVIDER = new DefaultAnnotationProcessorProvider();

    protected List<IAnnotationProcessor> processorList = new CopyOnWriteArrayList<>();

    private static class DefaultAnnotationProcessorProvider extends AbstractAnnotationProcessorProvider{
        public DefaultAnnotationProcessorProvider(){
            ServiceLoader<IAnnotationProcessor> list = ServiceLoader.load(IAnnotationProcessor.class);
            Iterator<IAnnotationProcessor> iterator = list.iterator();
            while (iterator.hasNext()){
                this.processorList.add(iterator.next());
            }
        }
    }

    public void registerProcessor(IAnnotationProcessor processor){
        processorList.add(processor);
    }

    public List<IAnnotationProcessor> getProcessorList(){
        return processorList;
    }
}

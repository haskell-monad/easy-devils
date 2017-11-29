package easy.devils.client.cluster;

import java.util.Set;

/**
 * 记录失败的节点信息
 * @author limengyu
 * @create 2017/11/29
 */
public class FailoverCheckManager<T> {

    private Set<T> failList;


    public void fail(){

    }

    public Set<T> failList(){
        return failList;
    }
}

package easy.devils.protocol;

import java.io.Serializable;

/**
 * payload
 * @author limengyu
 * @create 2017/11/21
 */
public class MetaInfo implements Serializable{
    private static final long serialVersionUID = 4092446890523646282L;

    private HaStrategyType haStrategyType;
    private LoadBalanceType loadBalanceType;
    private String group;
    private int weight;
    private int avgTime;
    private int total;
    private int successCount;
    private int maxTime;
    private int minTime;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public HaStrategyType getHaStrategyType() {
        return haStrategyType;
    }

    public void setHaStrategyType(HaStrategyType haStrategyType) {
        this.haStrategyType = haStrategyType;
    }

    public LoadBalanceType getLoadBalanceType() {
        return loadBalanceType;
    }

    public void setLoadBalanceType(LoadBalanceType loadBalanceType) {
        this.loadBalanceType = loadBalanceType;
    }

    public int getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(int avgTime) {
        this.avgTime = avgTime;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getMinTime() {
        return minTime;
    }

    public void setMinTime(int minTime) {
        this.minTime = minTime;
    }

    @Override
    public String toString() {
        return "MetaInfo{" +
                "haStrategyType=" + haStrategyType +
                ", loadBalanceType=" + loadBalanceType +
                ", avgTime=" + avgTime +
                ", total=" + total +
                ", successCount=" + successCount +
                ", maxTime=" + maxTime +
                ", minTime=" + minTime +
                '}';
    }
}

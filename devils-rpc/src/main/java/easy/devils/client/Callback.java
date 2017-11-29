package easy.devils.client;

/**
 * 回调接口
 * @author limengyu
 */
public interface Callback<T> {

	void onReceive(T message);
}

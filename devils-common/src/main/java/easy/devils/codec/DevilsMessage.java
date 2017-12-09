package easy.devils.codec;

/**
 * @author limengyu
 * @create 2017/11/18
 */
public class DevilsMessage<T>{
    private DevilsHeader devilsHeader;

    private T content;

    public DevilsMessage() {
    }

    public DevilsMessage(DevilsHeader devilsHeader, T content) {
        this.devilsHeader = devilsHeader;
        this.content = content;
    }

    public DevilsHeader getDevilsHeader() {
        return devilsHeader;
    }

    public void setDevilsHeader(DevilsHeader devilsHeader) {
        this.devilsHeader = devilsHeader;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}

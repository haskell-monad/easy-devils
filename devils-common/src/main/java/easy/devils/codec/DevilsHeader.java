package easy.devils.codec;

import easy.devils.common.DevilsConstant;
import easy.devils.protocol.VersionType;

/**
 * @author limengyu
 * @create 2017/11/9
 */
public class DevilsHeader {

    private short magic;
    private byte version;
    private byte extend;
    private Long messageId;
    private Integer messageSize;

    public DevilsHeader() {
    }

    public DevilsHeader(short magic, byte version) {
        this.magic = magic;
        this.version = version;
    }

    public DevilsHeader(short magic, byte version, byte extend) {
        this.magic = magic;
        this.version = version;
        this.extend = extend;
    }

    public short getMagic() {
        return magic;
    }

    public void setMagic(short magic) {
        this.magic = magic;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getExtend() {
        return extend;
    }

    public void setExtend(byte extend) {
        this.extend = extend;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageSize() {
        return messageSize;
    }

    public void setMessageSize(Integer messageSize) {
        this.messageSize = messageSize;
    }

    public static class Builder{

        private DevilsHeader devilsHeader;

        public static Builder createBuilder(){
            Builder builder = new Builder();
            builder.devilsHeader = new DevilsHeader(DevilsConstant.MAGIC,VersionType.V1.getVersion());
            return builder;
        }

        public Builder setMessageId(long messageId){
            devilsHeader.setMessageId(messageId);
            return this;
        }
        public Builder setExtend(byte extend){
            devilsHeader.setExtend(extend);
            return this;
        }
        public DevilsHeader builder(){
            return this.devilsHeader;
        }
    }

    public static void main(String[] args) {
        DevilsHeader builder = Builder.createBuilder().setMessageId(100L).builder();

        System.out.println(0 | 0);
        System.out.println(0 | (1 << 3));
        System.out.println(0 | (1 << 4));
        System.out.println("========================");
        System.out.println(1 | 0);
        System.out.println(1 | (1 << 3));
        System.out.println(1 | (1 << 4));
        System.out.println("========================");
        System.out.println(2 | 0);
        System.out.println(2 | (1 << 3));
        System.out.println(2 | (1 << 4));
        System.out.println("========================");
    }
}

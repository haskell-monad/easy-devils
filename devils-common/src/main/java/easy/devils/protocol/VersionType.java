package easy.devils.protocol;

/**
 * @author limengyu
 */
public enum VersionType {
	V1((byte) 1, 16),
    V2((byte) 2, 16);
	private byte version;
	private int headerLength;

	VersionType(byte version, int headerLength) {
		this.version = version;
		this.headerLength = headerLength;
	}
	public byte getVersion() {
		return version;
	}
	public int getHeaderLength() {
		return headerLength;
	}
}

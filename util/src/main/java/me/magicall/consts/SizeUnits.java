package me.magicall.consts;

public enum SizeUnits {
	BYTE(1), //
	KB(1024 * BYTE.size), //
	MB(1024 * KB.size), //
	GB(1024 * MB.size), //
	TB(1024 * GB.size), //
	;

	public final int size;

	private SizeUnits(final int size) {
		this.size = size;
	}

	public int toBytes(final int num) {
		return size * num;
	}

	public int toBytes() {
		return toBytes(1);
	}

	public int toOtherSize(final int num, final SizeUnits sizeUnits) {
		return size / sizeUnits.size * num;
	}
}

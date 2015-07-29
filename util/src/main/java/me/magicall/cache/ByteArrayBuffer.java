package me.magicall.cache;


public final class ByteArrayBuffer {
	private byte[] buffer;
	private int len;

	public ByteArrayBuffer(final int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Buffer capacity may not be negative");
		}
		buffer = new byte[capacity];
	}

	private void expand(final int newlen) {
		final byte[] newbuffer = new byte[Math.max(buffer.length << 1, newlen)];
		System.arraycopy(buffer, 0, newbuffer, 0, len);
		buffer = newbuffer;
	}

	public void append(final byte[] b, final int off, final int len) {
		if (b == null) {
			return;
		}
		if (off < 0 || off > b.length || len < 0 || off + len < 0 || off + len > b.length) {
			throw new IndexOutOfBoundsException();
		}
		if (len == 0) {
			return;
		}
		final int newlen = this.len + len;
		if (newlen > buffer.length) {
			expand(newlen);
		}
		System.arraycopy(b, off, buffer, this.len, len);
		this.len = newlen;
	}

	public void append(final int b) {
		final int newlen = len + 1;
		if (newlen > buffer.length) {
			expand(newlen);
		}
		buffer[len] = (byte) b;
		len = newlen;
	}

	public void append(final char[] b, final int off, final int len) {
		if (b == null) {
			return;
		}
		if (off < 0 || off > b.length || len < 0 || off + len < 0 || off + len > b.length) {
			throw new IndexOutOfBoundsException();
		}
		if (len == 0) {
			return;
		}
		final int oldlen = this.len;
		final int newlen = oldlen + len;
		if (newlen > buffer.length) {
			expand(newlen);
		}
		int i1 = off;
		for (int i2 = oldlen; i2 < newlen; ++i2) {
			buffer[i2] = (byte) b[i1];

			++i1;
		}

		this.len = newlen;
	}

	public void clear() {
		len = 0;
	}

	public byte[] toByteArray() {
		final byte[] b = new byte[len];
		if (len > 0) {
			System.arraycopy(buffer, 0, b, 0, len);
		}
		return b;
	}

	public int byteAt(final int i) {
		return buffer[i];
	}

	public int capacity() {
		return buffer.length;
	}

	public int length() {
		return len;
	}

	public byte[] buffer() {
		return buffer;
	}

	public void setLength(final int len) {
		if (len < 0 || len > buffer.length) {
			throw new IndexOutOfBoundsException();
		}
		this.len = len;
	}

	public boolean isEmpty() {
		return len == 0;
	}

	public boolean isFull() {
		return len == buffer.length;
	}
}
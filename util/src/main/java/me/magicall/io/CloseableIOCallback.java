package me.magicall.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

@FunctionalInterface
public interface CloseableIOCallback<C extends Closeable> {
	void callback(C closeable) throws IOException;

	@FunctionalInterface
	public static interface InputStreamCallback extends CloseableIOCallback<InputStream> {

	}

	@FunctionalInterface
	public static interface OutputSteamCallback extends CloseableIOCallback<OutputStream> {

	}

	@FunctionalInterface
	public static interface ReaderCallback extends CloseableIOCallback<Reader> {

	}

	@FunctionalInterface
	public static interface WriterCallback extends CloseableIOCallback<Writer> {

	}
}

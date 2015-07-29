package me.magicall.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public interface CloseableIOCallback<C extends Closeable> {
	void callback(C closeable) throws IOException;

	public static interface InputStreamCallback extends CloseableIOCallback<InputStream> {

	}

	public static interface OutputSteamCallback extends CloseableIOCallback<OutputStream> {

	}

	public static interface ReaderCallback extends CloseableIOCallback<Reader> {

	}

	public static interface WriterCallback extends CloseableIOCallback<Writer> {

	}
}

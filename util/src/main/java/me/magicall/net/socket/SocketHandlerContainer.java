package me.magicall.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.magicall.convenient.BaseHasList;
import me.magicall.io.IOUtil;

public class SocketHandlerContainer extends BaseHasList<SocketHandler> {

	private final ExecutorService pool;
	protected final int port;

	protected SocketHandlerContainer(final int port) {
		this(port, 0);
	}

	protected SocketHandlerContainer(final int port, final int poolSize) {
		this.port = port;
		pool = poolSize == 0 ? null : Executors.newFixedThreadPool(poolSize);
	}

	protected void handleSocket(final Socket socket) {
		if (pool == null) {
			handleSocketInternal(socket);
		} else {
			pool.execute(() -> handleSocketInternal(socket));
		}
	}

	protected void handleSocketInternal(final Socket socket) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			for (final SocketHandler h : list) {
				h.handleSocket(socket);
			}

			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			for (final SocketHandler h : list) {
				h.handleStream(inputStream, outputStream);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			SocketUtil.close(socket);
			IOUtil.close(inputStream);
			IOUtil.close(outputStream);
		}
	}
}

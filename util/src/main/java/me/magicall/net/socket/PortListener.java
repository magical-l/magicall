package me.magicall.net.socket;

import java.io.IOException;
import java.net.ServerSocket;

public class PortListener extends SocketHandlerContainer {

	private boolean listenOnce;

	private ServerSocket serverSocket;

	public PortListener(final int port, final int poolSize) {
		super(port, poolSize);
	}

	public PortListener(final int port, final int poolSize, final boolean listenOnce) {
		super(port, poolSize);
		this.listenOnce = listenOnce;
	}

	public void listen() throws IOException {
		close();
		serverSocket = new ServerSocket(port);
		System.out.println("@@@@@@PortListener.listen():listening " + port);
		new Thread() {
			@Override
			public void run() {
				do {
					try {
						handleSocket(serverSocket.accept());
					} catch (final IOException e) {
						e.printStackTrace();
					}
				} while (serverSocket != null && !serverSocket.isClosed() && !isListenOnce());
			}

		}.start();
	}

	public void setListenOnce(final boolean listenOnce) {
		this.listenOnce = listenOnce;
	}

	public boolean isListenOnce() {
		return listenOnce;
	}

	public void close() {
		SocketUtil.close(serverSocket);
		serverSocket = null;
	}

	public int getPort() {
		return port;
	}
}

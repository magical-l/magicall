package me.magicall.net.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends SocketHandlerContainer {

	private final String host;

	public Client(final String host, final int port) {
		this(host, port, 0);
	}

	public Client(final String host, final int port, final int poolSize) {
		super(port, poolSize);
		this.host = host;
	}

	public void connect() throws IOException, UnknownHostException {
		handleSocket(new Socket(host, port));
	}
}
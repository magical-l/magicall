package me.magicall.net.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface SocketHandler {

	void handleSocket(Socket socket);

	void handleStream(InputStream inputStream, OutputStream outputStream);

}

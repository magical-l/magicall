package me.magicall.net.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketUtil {

	public static IOException close(final Socket socket) {
		if (socket != null) {
			try {
				socket.close();
			} catch (final IOException e) {
				e.printStackTrace();
				return e;
			}
		}
		return null;
	}

	public static IOException close(final ServerSocket serverSocket) {
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (final IOException e) {
				e.printStackTrace();
				return e;
			}
		}
		return null;
	}
}

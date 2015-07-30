package me.magicall.net.socket;

import me.magicall.io.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

public class ObjectSenderClient {
	private final String host;
	private final int port;

	public ObjectSenderClient(final String host, final int port) {
		this.host = host;
		this.port = port;
	}

	public Object send(final Serializable obj) throws IOException, UnknownHostException {
		final Client client = new Client(host, port);

		class SendObjSocketHandler implements SocketHandler {

			private ObjectOutputStream objectOutputStream;
			private Object returnFromServer;

			public SendObjSocketHandler() {
				super();
			}

			@Override
			public void handleSocket(final Socket socket) {
			}

			@Override
			public void handleStream(final InputStream inputStream, final OutputStream outputStream) {
				try {
					objectOutputStream = new ObjectOutputStream(outputStream);
					objectOutputStream.writeObject(obj);
					objectOutputStream.flush();

					// 注:关闭ObjectInputStream将关闭它包装的InputStream,而关闭这个从Socket中来的inputStream将关闭Socket!
					// 因此此时这个ObjectInputStream不能close!我靠
					final ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
					final Object obj = objectInputStream.readObject();
					returnFromServer = obj;
				} catch (final IOException e) {
					e.printStackTrace();
				} catch (final ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		final SendObjSocketHandler socketHandler = new SendObjSocketHandler();
		try {
			client.add(socketHandler);
			client.connect();
			return socketHandler.returnFromServer;
		} finally {
			IOUtil.close(socketHandler.objectOutputStream);
		}
	}
}

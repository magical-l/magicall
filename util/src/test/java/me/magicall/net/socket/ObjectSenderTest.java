package me.magicall.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ObjectSenderTest {

	public static void main(final String... args) throws Exception {
		final int port = 8848;
		// 启动一个线程作为"服务器"
		try {
			final PortListener p = new PortListener(port, 1);
			p.add(new SocketHandler() {
				@Override
				public void handleSocket(final Socket socket) {
				}

				@Override
				public void handleStream(final InputStream inputStream, final OutputStream outputStream) {
					try {
						final ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
						final Object receive = objectInputStream.readObject();
						System.out.println("@@@@@@PortListener.main():receive:" + receive);
						final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
						objectOutputStream.writeObject(receive);
						objectOutputStream.flush();
					} catch (final IOException e) {
						e.printStackTrace();
					} catch (final ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			});

			p.listen();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
//			p.close();
		}

		// 主线程作为"客户端",每秒请求一次"服务器",发送一个字符串给它
		final ObjectSenderClient client = new ObjectSenderClient("127.0.0.1", port);
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			final Object returnFromServer = client.send("abcd");
			System.out.println("@@@@@@client:return from server:" + returnFromServer);
		}
	}
}

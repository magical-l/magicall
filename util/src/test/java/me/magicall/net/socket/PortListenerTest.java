package me.magicall.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import me.magicall.util.kit.StrKit;

import org.junit.Test;

public class PortListenerTest {

	@Test
	public void testListen() {
		main();
	}

	public static void main(final String... strings) {
		final PortListener p = new PortListener(80, 1);

		p.add(new SocketHandler() {
			@Override
			public void handleSocket(final Socket socket) {
			}

			@Override
			public void handleStream(final InputStream inputStream, final OutputStream outputStream) {
				final Scanner scanner = new Scanner(inputStream);

				final StringBuilder sb = new StringBuilder();
				while (scanner.hasNextLine()) {
					final String nextLine = scanner.nextLine();
					sb.append(nextLine).append(StrKit.newLine());
					System.out.println("@@@@@@PortListener.main():" + nextLine);
					if (nextLine.trim().equals("")) {
						break;
					}
				}
				try {
					outputStream.write(sb.toString().getBytes());
					outputStream.flush();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		});

		try {
			p.listen();
			System.out.println("@@@@@@PortListener.main():listen end");
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
//			p.close();
		}

	}
}

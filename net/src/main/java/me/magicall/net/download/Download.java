package me.magicall.net.download;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Date;

import me.magicall.net.http.RequestHeader;
import me.magicall.net.http.UrlConnector;
import me.magicall.net.http.UrlConnectorConfig;
import me.magicall.net.http.UrlConnectorContentHandler;
import me.magicall.util.time.TimeFormatter;
import me.magicall.util.time.TimeUtil;

public class Download {

	private static class SetPart implements UrlConnectorContentHandler {

		int start;
		int end;

		public SetPart(final int start, final int end) {
			super();
			this.start = start;
			this.end = end;
		}

		@Override
		public void beforeConnect(final HttpURLConnection conn) {
			RequestHeader.Range.set(conn, "bytes=" + start + '-' + (end - 1));
		}

		@Override
		public void handleData(final byte[] bs) {
		}
	}

	private class DataCacher implements UrlConnectorContentHandler {

		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		@Override
		public void beforeConnect(final HttpURLConnection conn) {
		}

		@Override
		public void handleData(final byte[] bs) {
			try {
				byteArrayOutputStream.write(bs);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

//	private int threadsCount;
	private final UrlConnectorConfig config;
	private File target;

	public Download(final UrlConnectorConfig config) {
		this.config = config;
	}

	public void download() {
		checkDir();
		final DataCacher dataCacher = new DataCacher();
		final int start = 0;
		final int end = 100;
		OutputStream out = null;
//		new Thread() {
//			@Override
//			public void run() {
		try {
			final UrlConnector connector = new UrlConnector(config);
			connector.add(new SetPart(start, end));
			connector.add(dataCacher);
			connector.downBytes();
			out = new FileOutputStream(target);
			dataCacher.byteArrayOutputStream.writeTo(out);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
//			}
//		}.start();
	}

	private void checkDir() {
		final File parent = target.getParentFile();
		if (!parent.exists()) {
			if (!parent.mkdirs()) {
				System.out.println("@@@@@@mkdirs fail");
				return;
			}
		}
	}

	public File getTarget() {
		return target;
	}

	public void setTarget(final File target) {
		this.target = target;
	}

	public static void main(final String... args) {
		System.out.println("@@@@@@start");
		final UrlConnectorConfig config = new UrlConnectorConfig();
		config.setConnectTimeout((int) TimeUtil.MINUTE);
//		config.setCookies(null);
		config.setReadTimeout((int) TimeUtil.MINUTE);
		config.setUrl("http://img.24meinv.com/hdmv/simei520/simei8/DSC00020%20.jpg");

		final Download download = new Download(config);
		download.setTarget(new File("d:\\" + TimeFormatter.Y4M2D2H2MIN2S2MS3.format(new Date()) + ".jpg"));
		download.download();
	}
}

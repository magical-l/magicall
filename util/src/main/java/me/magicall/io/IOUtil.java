package me.magicall.io;

import me.magicall.lang.FinallyCallback;
import me.magicall.lang.exception.ExceptionHandler;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class IOUtil {

	public static void withLine(final File file, final LineHandler lineHandler,
			final ExceptionHandler<IOException> exceptionHandler, final FinallyCallback finallyCallback)
			throws RuntimeException {
		io(() -> new Scanner(file), scanner -> {
            while (scanner.hasNextLine()) {
                lineHandler.handle(scanner.nextLine());
            }
        }, exceptionHandler, finallyCallback);
	}

	public static <C extends Closeable> void io(final CloseableCreater<C> creater, final CloseableIOCallback<C> callback)
			throws RuntimeException {
		io(creater, callback, null);
	}

	public static <C extends Closeable> void io(final CloseableCreater<C> creater,
			final CloseableIOCallback<C> callback, final ExceptionHandler<IOException> exceptionHandler)
			throws RuntimeException {
		final CloseableCreaterWithFinallyCloseCallback<C> c = new CloseableCreaterWithFinallyCloseCallback<>(creater);
		io(c, callback, exceptionHandler, c);
	}

	public static <C extends Closeable> void io(final CloseableCreater<C> creater,
			final CloseableIOCallback<C> callback, final ExceptionHandler<IOException> exceptionHandler,
			final FinallyCallback finallyCallback) throws RuntimeException {
		C closeable = null;
		try {
			closeable = creater.create();
			callback.callback(closeable);
		} catch (final IOException e) {
			if (exceptionHandler != null) {
				exceptionHandler.handle(e);
			}
		} finally {
			if (finallyCallback != null) {
				finallyCallback.finallyExecute();
			}
		}
	}

	/**
	 * 获取一个文件,如果不存在,将创建之.如果其路径中有的父文件夹不存在,也会创建.
	 * 
	 * @param path
	 * @return
	 */
	public static File getFile(final String path) {
		final File f = new File(path);
		if (f.exists()) {
			return f;
		}
		final File dir = f.getParentFile();
		if (!dir.exists()) {
			if (!tryMakeDirs(dir)) {
				return null;
			}
		}
		try {
			f.createNewFile();
			return f;
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean tryMakeDirs(final File dir) {
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				return false;
			}
		} else if (dir.isFile()) {
			return false;
		}
		return true;
	}

	public static void close(final Scanner scanner) {
		if (scanner != null) {
			scanner.close();
		}
	}

	public static IOException close(final Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (final IOException e) {
				e.printStackTrace();
				return e;
			}
		}
		return null;
	}

	private static class CloseableCreaterWithFinallyCloseCallback<C extends Closeable> implements CloseableCreater<C>,
			FinallyCallback {
		private final CloseableCreater<C> creater;
		private C closeable;

		CloseableCreaterWithFinallyCloseCallback(final CloseableCreater<C> creater) {
			super();
			this.creater = creater;
		}

		@Override
		public void finallyExecute() {
			close(closeable);
		}

		@Override
		public C create() throws IOException {
			closeable = creater.create();
			return closeable;
		}
	}

	@FunctionalInterface
	public interface LineHandler {
		void handle(String line) throws IOException;
	}
}

package me.magicall.algorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Md5Util {
	public static void main(final String... args) {
		System.out.println("@@@@@@Md5Util.main():" + md5("123456"));
	}

	public static String md5(final byte[] b) {
		final StringBuilder sb = new StringBuilder();
		for (final byte element : b) {
			int i = element;
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(i));
		}
		return sb.toString();
	}

	public static String md5(final Object... objects) {
		return md5(Arrays.toString(objects));
	}

	public static String md5(final String plainText) {
		if (plainText != null) {
			try {
				final MessageDigest md = MessageDigest.getInstance("MD5");
				final byte[] b = md.digest(plainText.getBytes());
				return md5(b);
			} catch (final NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String md5(final File file) {
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			final byte[] b = new byte[8192];
			int length;
			final MessageDigest md = MessageDigest.getInstance("MD5");
			while ((length = fin.read(b)) != -1) {
				md.update(b, 0, length);
			}
			return md5(md.digest());
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}

package me.magicall.web.util;

import me.magicall.cache.ByteArrayBuffer;
import me.magicall.io.IOUtil;
import me.magicall.util.encode.EncodeUtil;
import me.magicall.util.kit.Kits;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UploadDownload {

	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param absolutePath 文件的绝对路径
	 * @throws Exception
	 */
	public static void download(final HttpServletResponse response, final String absolutePath)//
			throws Exception {
		final File f = new File(absolutePath);
		download(response, f);
	}

	/**
	 * @param response
	 * @param f
	 */
	public static void download(final HttpServletResponse response, final File f) {
		if (!f.exists()) {
			throw new RuntimeException("文件已丢失,请联系管理员");
		}
		final long fileLen = f.length();
		final String fileShortName = Kits.STR.subStringAfterLastSeq(f.getAbsolutePath(), File.separator);

		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + EncodeUtil.encode(fileShortName));
		response.setHeader("Content-Length", String.valueOf(fileLen));

		final ByteArrayBuffer buffer = new ByteArrayBuffer((int) fileLen);//XXX:file可能过大否?
		IOUtil.io(() -> new BufferedInputStream(new FileInputStream(f)), in -> {
            final byte[] bs = new byte[2048];
            for (int read = in.read(bs); read != -1; read = in.read(bs)) {
                buffer.append(bs, 0, read);
            }
        }, e -> {
            if (e instanceof FileNotFoundException) {
                throw new RuntimeException("文件" + fileShortName + "已丢失,请联系管理员。");
            } else {
                throw new RuntimeException("读取文件" + fileShortName + "出错,请稍后再试");
            }
        });

		if (!buffer.isEmpty()) {
			IOUtil.io(response::getOutputStream, out -> {
                out.write(buffer.buffer());
                out.flush();
            }, e -> {
                throw new RuntimeException("处理文件" + fileShortName + "出错,请稍后再试");
            });
		}
	}
}

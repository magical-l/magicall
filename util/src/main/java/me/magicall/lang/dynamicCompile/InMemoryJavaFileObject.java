package me.magicall.lang.dynamicCompile;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import me.magicall.io.IOUtil;

public class InMemoryJavaFileObject extends SimpleJavaFileObject {
	private String contents = null;

	public InMemoryJavaFileObject(final String className, final String contents) {
		super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
		this.contents = contents;
	}

	@Override
	public CharSequence getCharContent(final boolean ignoreEncodingErrors) throws IOException {
		return contents;
	}

	public static void main(final String... args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		final String className = "TTT";
		final String codeString = "public class TTT{public String toString(){return \"haha\";}}";
		final String classOutputFolder = "d:\\";

		// 通过 ToolProvider 取得 JavaCompiler 对象，JavaCompiler 对象是动态编译工具的主要对象
		final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = null;
		try {
			// 通过 JavaCompiler 取得标准 StandardJavaFileManager 对象，StandardJavaFileManager 对象主要负责
			// 编译文件对象的创建，编译的参数等等，我们只对它做些基本设置比如编译 CLASSPATH 等。
			fileManager = compiler.getStandardFileManager(null, null, null);

			// 因为是从内存中读取 Java 源文件，所以需要创建我们的自己的 JavaFileObject，即 InMemoryJavaFileObject 
			final JavaFileObject fileObject = new InMemoryJavaFileObject(className, codeString);
			final Iterable<? extends JavaFileObject> files = Arrays.asList(fileObject);

			// 编译结果信息的记录
			final StringWriter sw = new StringWriter();

			// 编译目的地设置
			final Iterable<String> options = Arrays.asList("-d", classOutputFolder);

			// 通过 JavaCompiler 对象取得编译 Task 
			final CompilationTask task = compiler.getTask(sw, fileManager, null, options, null, files);

			// 调用 call 命令执行编译，如果不成功输出错误信息
			if (!task.call()) {
				final String failedMsg = sw.toString();
				System.out.println("Build Error:" + failedMsg);
			}

			final Class<?> c = fileManager.getClassLoader(StandardLocation.CLASS_OUTPUT).loadClass(className);
			final Object o = c.newInstance();
			System.out.println("@@@@@@InMemoryJavaFileObject.main():" + o);
		} finally {
			IOUtil.close(fileManager);
		}

	}
}
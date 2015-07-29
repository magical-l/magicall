package me.magicall.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import me.magicall.consts.StrConst.EncodingConst;
import me.magicall.util.kit.Kits;
import me.magicall.util.kit.PrimitiveKit;

public class ClassUtil {
	private static final char ARR_CLASS_START_CHAR = '[';
	private static final char OBJ_ARR_FLAG_CHAR = 'L';
	private static final char OBJ_ARR_CLASS_END_CHAR = ';';

	public static Class<?> getClass(final String className) {
		try {
			return Class.forName(className);
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param object
	 * @param fieldValue
	 */
	public static void setField(final Object object, final Object fieldValue) {
		final Class<?> fieldClass = fieldValue.getClass();

		final Class<?> objClass = object.getClass();
		final Method setter = MethodUtil.getSetterIgnoreNameCaseAndTypeAssigned(objClass, fieldClass);
		if (setter != null) {
			MethodUtil.invokeMethod(object, setter, fieldValue);
		}

		final Method adder = MethodUtil.getAdder(objClass, fieldClass);
		if (adder != null) {
			MethodUtil.invokeMethod(object, adder, fieldValue);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(final ClassLoader classLoader, final String className, final Object... constructorArgs) {
		try {
			return (T) newInstance(Class.forName(className, true, classLoader), constructorArgs);
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(final String className, final Object... constructorArgs) {
		try {
			return (T) newInstance(Class.forName(className), constructorArgs);
		} catch (final ClassNotFoundException e) {
//			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 不会抛出异常，如果创建中有异常，将返回null
	 * 
	 * @param <T>
	 * @param clazz
	 * @param constructorArgs
	 * @return
	 */
	public static <T> T newInstance(final Class<T> clazz, final Object... constructorArgs) {
		final Constructor<T> constructor = getConstructor(clazz, constructorArgs);
		if (constructor != null) {
			try {
				return constructor.newInstance(constructorArgs);
			} catch (final InstantiationException e) {
				e.printStackTrace();
			} catch (final IllegalAccessException e) {
				e.printStackTrace();
			} catch (final IllegalArgumentException e) {
				e.printStackTrace();
			} catch (final InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static <T> Constructor<T> getConstructor(final Class<T> clazz, final Object... constructorArgs) {
		final Constructor<T> constructor = getStrictConstructor(clazz, constructorArgs);
		return constructor == null ? findCompatibleConstructor(clazz, constructorArgs) : constructor;
	}

	public static <T> Constructor<T> findCompatibleConstructor(final Class<T> clazz, final Object... constructorArgs) {
		@SuppressWarnings("unchecked")
		final Constructor<T>[] constructors = (Constructor<T>[]) clazz.getConstructors();
		final Class<?>[] argsTypes = objsToClasses(constructorArgs);
		Constructor<T> c = null;
		for (final Constructor<T> constructor : constructors) {
			final Class<?>[] parametersTypes = constructor.getParameterTypes();
			if (parametersTypes.length == argsTypes.length) {
				if (Arrays.equals(parametersTypes, argsTypes)) {
					return constructor;
				}
				if (checkAllAssignable(parametersTypes, constructorArgs)) {
					c = constructor;
				}
			}
		}
		return c;
	}

	public static <T> Constructor<T> getStrictConstructor(final Class<T> clazz, final Object... constructorArgs) {
		final Class<?>[] argsTypes = objsToClasses(constructorArgs);
		try {
			return clazz.getConstructor(argsTypes);
		} catch (final NoSuchMethodException e) {
			e.printStackTrace();
		} catch (final SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检查第二个参数的每一个Class是否都是第一个参数的对应位置的Class的子类（包括自己）。
	 * 
	 * @param superClasses
	 * @param subClasses
	 * @return
	 */
	public static boolean checkAllAssignable(final Class<?>[] superClasses, final Class<?>... subClasses) {
		if (superClasses.length != subClasses.length) {
			return false;
		}
		int index = 0;
		for (final Class<?> c : superClasses) {
			if (!c.isAssignableFrom(subClasses[index])) {
				return false;
			}
			++index;
		}
		return true;
	}

	/**
	 * 检查第二个参数的每一个对象是否都是第一个参数对应位置Class的实例。
	 * 
	 * @param superClasses
	 * @param objects
	 * @return
	 */
	public static boolean checkAllAssignable(final Class<?>[] superClasses, final Object... objects) {
		return checkAllAssignable(superClasses, objsToClasses(objects));
	}

	/**
	 * 将对象数组转化成它们的Class对象组成的数组，位置一一对应。
	 * 
	 * @param objs
	 * @return
	 */
	public static Class<?>[] objsToClasses(final Object... objs) {
		final Class<?>[] types = new Class<?>[objs.length];
		int index = 0;
		for (final Object o : objs) {
			types[index] = o.getClass();
			++index;
		}
		return types;
	}

	/**
	 * 获取对象实现的所有接口的Class对象，包括父类声明实现接口，包括接口的父接口
	 * 
	 * @param o
	 * @return
	 */
	public static Collection<Class<?>> getAllInterfaces(final Object o) {
		return getAllInterfaces(o.getClass());
	}

	/**
	 * 获取此Class对象所代表的类实现的所有接口的Class对象，包括父类声明实现接口，包括接口的父接口
	 * 
	 * @param clazz
	 * @return
	 */
	public static Collection<Class<?>> getAllInterfaces(final Class<?> clazz) {
		final Collection<Class<?>> rt = new LinkedHashSet<>();
		for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
			final Class<?>[] ifcs = clazz.getInterfaces();
			for (final Class<?> ifc : ifcs) {
				rt.add(ifc);
				rt.addAll(getAllInterfaces(ifc));
			}
		}
		return rt;
	}

	private static Class<?> arrClass(final String componentTypeName, final int dim) {
		final StringBuilder sb = new StringBuilder();
		for (int i = dim; i > 0; --i) {
			sb.append(ARR_CLASS_START_CHAR);
		}
		sb.append(componentTypeName);
		try {
			return Class.forName(sb.toString());
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Class<?> primitiveArrClass(final PrimitiveKit<?, ?> primitiveKit, final int dim) {
		return arrClass(primitiveKit.getPrimitiveArrFlag(), dim);
	}

	public static Class<?> arrClass(final Class<?> clazz, final int dim) {
		if (dim < 1) {
			throw new IllegalArgumentException("dim should >= 1 but now it's " + dim);
		}
		final int factDim = clazz.isArray() ? dim + dimOfArrayClass(clazz) : dim;
		final Class<?> componentType = componentType(clazz);
		return componentType.isPrimitive()//
		? arrClass(Kits.getPrimitiveKit(componentType).getPrimitiveArrFlag(), factDim)//
				: arrClass(OBJ_ARR_FLAG_CHAR + componentType.getName() + OBJ_ARR_CLASS_END_CHAR, factDim);

	}

	/**
	 * 返回数组的具体元素的class(对多维数组适用)
	 * 
	 * @param arrClass
	 * @return
	 */
	public static Class<?> componentType(final Class<?> arrClass) {
		Class<?> c = arrClass;
		while (c.isArray()) {
			c = c.getComponentType();
		}
		return c;
	}

	/**
	 * 给定一个Class对象，如果它表示一个数组类，则返回它所表示的数组的维数，如果它不是，返回0
	 * 
	 * @param arrClass
	 * @return
	 */
	public static int dimOfArrayClass(final Class<?> arrClass) {
		return Kits.CHAR.elementCount(ARR_CLASS_START_CHAR, arrClass.getName().toCharArray());
	}

	/**
	 * 从包package中获取所有的Class
	 * 
	 * @param pack
	 * @return
	 */
	public static Set<Class<?>> getClasses(final Package pack) {
		//第一个class类的集合
		final Set<Class<?>> classes = new LinkedHashSet<>();
		//是否循环迭代
		final boolean recursive = true;
		//获取包的名字 并进行替换
		String packageName = pack.getName();
		final String packageDirName = packageName.replace('.', '/');
		//定义一个枚举的集合 并进行循环来处理这个目录下的things
		final Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			//循环迭代下去
			while (dirs.hasMoreElements()) {
				//获取下一个元素
				final URL url = dirs.nextElement();
				//得到协议的名称
				final String protocol = url.getProtocol();
				//如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					//获取包的物理路径
					final String filePath = URLDecoder.decode(url.getFile(), EncodingConst.UTF8);
					//以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
				} else if ("jar".equals(protocol)) {
					//如果是jar包文件 
					//定义一个JarFile
					final JarFile jar;
					try {
						//获取jar
						jar = ((JarURLConnection) url.openConnection()).getJarFile();
						//从此jar包 得到一个枚举类
						final Enumeration<JarEntry> entries = jar.entries();
						//同样的进行循环迭代
						while (entries.hasMoreElements()) {
							//获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							final JarEntry entry = entries.nextElement();
							String name = entry.getName();
							//如果是以/开头的
							if (name.charAt(0) == '/') {
								//获取后面的字符串
								name = name.substring(1);
							}
							//如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								final int idx = name.lastIndexOf('/');
								//如果以"/"结尾 是一个包
								if (idx != -1) {
									//获取包名 把"/"替换成"."
									packageName = name.substring(0, idx).replace('/', '.');
								}
								//如果可以迭代下去 并且是一个包
								if (idx != -1 || recursive) {
									//如果是一个.class文件 而且不是目录
									if (name.endsWith(".class") && !entry.isDirectory()) {
										//去掉后面的".class" 获取真正的类名
										final String className = name.substring(packageName.length() + 1, name.length() - 6);
										try {
											//添加到classes
											classes.add(Class.forName(packageName + '.' + className));
										} catch (final ClassNotFoundException e) {
//                                            log.error("添加用户自定义视图类错误 找不到此类的.class文件");
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (final IOException e) {
//                        log.error("在扫描用户定义视图时从jar包获取文件出错");
						e.printStackTrace();
					}
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return classes;
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile(final String packageName, final String packagePath, final boolean recursive, final Set<Class<?>> classes) {
		//获取此包的目录 建立一个File
		final File dir = new File(packagePath);
		//如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
//            log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			System.out.println("@@@@@@GetClassFromPackage.findAndAddClassesInPackageByFile():");
			return;
		}
		//如果存在 就获取包下的所有文件 包括目录
		final File[] dirfiles = dir.listFiles(
				file -> recursive && file.isDirectory() || file.getName().endsWith(".class"));
		//循环所有文件
		for (final File file : dirfiles) {
			//如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + '.' + file.getName(), file.getAbsolutePath(), recursive, classes);
			} else {
				//如果是java类文件 去掉后面的.class 只留下类名
				final String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					//添加到集合中去
					classes.add(Class.forName(packageName + '.' + className));
				} catch (final ClassNotFoundException e) {
//                    log.error("添加用户自定义视图类错误 找不到此类的.class文件");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(final String... args) {
//		p(Object.class, 1);
//		p(Object.class, 2);
//		p(int.class, 1);
//		p(int.class, 2);
//		p(Object[].class, 1);
//		p(Object[].class, 2);
//		p(int[].class, 1);
//		p(int[].class, 2);

//		final Class<?> c = String[][][].class;
//		System.out.println(componentType(c) + " " + c.getComponentType());
//		System.out.println(int[].class.getComponentType());
//		System.out.println(int[][].class.getComponentType());
//		System.out.println(int[][][].class.getComponentType());

		System.out.println(arrClass(String.class, 1) == String[].class);
		System.out.println(arrClass(String.class, 2) == String[][].class);
		System.out.println(arrClass(int.class, 1) == int[].class);
		System.out.println(arrClass(int.class, 2) == int[][].class);

		System.out.println(arrClass(String[].class, 1) == String[][].class);
		System.out.println(arrClass(String[].class, 2) == String[][][].class);
		System.out.println(arrClass(int[].class, 1) == int[][].class);
		System.out.println(arrClass(int[].class, 2) == int[][][].class);

		System.out.println(arrClass(String[][].class, 1) == String[][][].class);
		System.out.println(arrClass(String[][].class, 2) == String[][][][].class);
		System.out.println(arrClass(int[][].class, 1) == int[][][].class);
		System.out.println(arrClass(int[][].class, 2) == int[][][][].class);
	}

	static void p(final Class<?> componentClassType, final int dim) {
		final Class<?> arrClass = arrClass(componentClassType, dim);
		System.out.println(arrClass);
		final Object arr = Array.newInstance(arrClass, 0);
		System.out.println(arr);
	}

}

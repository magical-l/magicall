package me.magicall.util;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 一些关于包的但是可能没什么用的工具方法。
 * 注：没有“初始化”过的包是不存在的。比如me.magicall这个包,必须“访问过”该包下的类，它才存在。
 * 
 * @author MaGiCalL
 */
public class PackageUtil {

	public static boolean isAnnotationPresentInHierarchy(final Package pack, final Class<? extends Annotation> annotationClass) {
		Package p = pack;
		if (p.isAnnotationPresent(annotationClass)) {
			return true;
		}
		for (p = parent(p); p != null; p = parent(p)) {
			if (p.isAnnotationPresent(annotationClass)) {
				return true;
			}
		}
		return false;
	}

	public static Package parent(final Package pack) {
		final String name = pack.getName();
		final int index = name.lastIndexOf('.');
		if (index < 0) {
			return null;
		}
		return Package.getPackage(name.substring(0, index));
	}

	public static Package[] parents(final Package pack) {
		final List<Package> list = new LinkedList<>();
		for (Package p = parent(pack); p != null; p = parent(p)) {
			list.add(p);
		}
		return list.toArray(new Package[list.size()]);
	}

	public static Package[] parents(final Class<?> clazz) {
		final List<Package> list = new LinkedList<>();
		for (Package p = clazz.getPackage(); p != null; p = parent(p)) {
			list.add(p);
		}
		return list.toArray(new Package[list.size()]);
	}

	public static boolean belongs(final Class<?> clazz, final Package pack) {
		Package p = clazz.getPackage();
		if (p == pack) {
			return true;
		}
		if (pack == null) {
			assert p != null;
			return false;
		}
		for (p = parent(p); p != null; p = parent(p)) {
			if (p.equals(pack)) {
				return true;
			}
		}
		return false;
	}

	public static void main(final String... args) {
		System.out.println("@@@@@@PackageUtil.main():" + Package.getPackage("me.magicall"));
		System.out.println("@@@@@@PackageUtil.main():" + Arrays.toString(parents(PackageUtil.class)));
	}
}

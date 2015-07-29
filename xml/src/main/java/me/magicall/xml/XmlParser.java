package me.magicall.xml;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

public interface XmlParser {

	/**
	 * 解析一个xml文件，按节点映射规则生成对应的节点对象，返回根节点的对象。
	 * 节点映射规则：
	 * 1，先从节点名-类名映射规则Map中寻找。这个映射Map可以通过构造时或者setMap方法添加进来。查找时对节点名不区分大小写。
	 * 2，如果没有配置相应的映射规则，则使用“默认包名”和节点名的组合作为该类的类名。默认包名可以通过构造时或者setPackageName设置。
	 * 
	 * @param <T>
	 * @param xmlFile
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ClassCastException
	 */
	<T> T parse(final File xmlFile) throws IllegalArgumentException, ClassCastException;

	/**
	 * 解析一个xml文件，按节点映射规则生成对应的节点对象，返回根节点的对象。
	 * 节点映射规则：
	 * 1，先从节点名-类名映射规则Map中寻找。这个映射Map可以通过构造时或者setMap方法添加进来。查找时对节点名不区分大小写。
	 * 2，如果没有配置相应的映射规则，则使用“默认包名”和节点名的组合作为该类的类名。默认包名可以通过构造时或者setPackageName设置。
	 * 
	 * @param <T>
	 * @param xmlPath
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ClassCastException
	 */
	<T> T parse(final String xmlPath) throws IllegalArgumentException, ClassCastException;

	/**
	 * 解析一个xml文件，按节点映射规则生成对应的节点对象，返回根节点的对象。解析之前会根据xsdPath参数提供的xsd文件进行校验
	 * 节点映射规则：
	 * 1，先从节点名-类名映射规则Map中寻找。这个映射Map可以通过构造时或者setMap方法添加进来。查找时对节点名不区分大小写。
	 * 2，如果没有配置相应的映射规则，则使用“默认包名”和节点名的组合作为该类的类名。默认包名可以通过构造时或者setPackageName设置。
	 * 
	 * @param <T>
	 * @param xmlPath
	 * @param xsdPath
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ClassCastException
	 */
	<T> T parse(final String xmlPath, final String xsdPath) throws IllegalArgumentException, ClassCastException;

	/**
	 * 解析一个xml文件，按节点映射规则生成对应的节点对象，返回根节点的对象。解析之前会根据xsdFile参数提供的xsd文件进行校验
	 * 节点映射规则：
	 * 1，先从节点名-类名映射规则Map中寻找。这个映射Map可以通过构造时或者setMap方法添加进来。查找时对节点名不区分大小写。
	 * 2，如果没有配置相应的映射规则，则使用“默认包名”和节点名的组合作为该类的类名。默认包名可以通过构造时或者setPackageName设置。
	 * 
	 * @param xmlFile
	 * @param xsdFile
	 * @return
	 * @throws IllegalArgumentException
	 */
	<T> T parse(final File xmlFile, final File xsdFile) throws IllegalArgumentException, ClassCastException;

	<T> T parse(InputStream inputStream);

	<T> T parse(Reader reader);

}
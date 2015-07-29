package me.magicall.xml.dom4j;

import java.util.Map;

/**
 * 在给定的map中，以节点名作为key寻找对应的java bean的类全名。
 * 
 * @author MaGiCalL
 */
public class MappingNodeNameToClassNameXmlParser extends AbsXmlParser {

	private final Map<String, String> nodeNameClassNameMap;

	public MappingNodeNameToClassNameXmlParser(final Map<String, String> nodeNameClassNameMap) {
		super();
		this.nodeNameClassNameMap = nodeNameClassNameMap;
	}

	@Override
	protected String nodeNameToClassName(final String nodeName) {
		return nodeNameClassNameMap.get(nodeName);
	}
}

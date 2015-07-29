package me.magicall.xml.dom4j;

/**
 * 将节点名视作类短名，在指定的包名中寻找java bean
 * 
 * @author MaGiCalL
 */
public class PackageBasedXmlParser extends AbsXmlParser {

	private final String basePackage;

	public PackageBasedXmlParser(final String basePackage) {
		super();
		this.basePackage = basePackage.endsWith(".") ? basePackage : basePackage + '.';
	}

	@Override
	protected String nodeNameToClassName(final String nodeName) {
		return basePackage + Character.toUpperCase(nodeName.charAt(0)) + nodeName.substring(1);
	}
}

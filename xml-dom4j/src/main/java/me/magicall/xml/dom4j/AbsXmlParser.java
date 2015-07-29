package me.magicall.xml.dom4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import me.magicall.util.BeanUtil;
import me.magicall.util.ClassUtil;
import me.magicall.util.MethodUtil;
import me.magicall.util.kit.Kits;
import me.magicall.xml.XmlParser;
import me.magicall.xml.XmlValidator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

/**
 * 暂时不支持横杠、下划线
 * 
 * @author MaGiCalL
 */
public abstract class AbsXmlParser implements XmlParser {

	public AbsXmlParser() {
		super();
	}

	@Override
	public <T> T parse(final InputStream inputStream) {
		return parse(parseDocument(inputStream));
	}

	@Override
	public <T> T parse(final Reader reader) {
		return parse(parseDocument(reader));
	}

	/*
	 * (non-Javadoc)
	 * @see me.magicall.xml.XmlParser#parse(java.io.File)
	 */
	@Override
	public <T> T parse(final File xmlFile) throws IllegalArgumentException, ClassCastException {
		return parse(xmlFile, null);
	}

	/*
	 * (non-Javadoc)
	 * @see me.magicall.xml.XmlParser#parse(java.lang.String)
	 */
	@Override
	public <T> T parse(final String xmlPath) throws IllegalArgumentException, ClassCastException {
		return parse(new File(xmlPath));
	}

	/*
	 * (non-Javadoc)
	 * @see me.magicall.xml.XmlParser#parse(java.lang.String, java.lang.String)
	 */
	@Override
	public <T> T parse(final String xmlPath, final String xsdPath) throws IllegalArgumentException, ClassCastException {
		return parse(new File(xmlPath), new File(xsdPath));
	}

	/*
	 * (non-Javadoc)
	 * @see me.magicall.xml.XmlParser#parse(java.io.File, java.io.File)
	 */
	@Override
	public <T> T parse(final File xmlFile, final File xsdFile) throws IllegalArgumentException, ClassCastException {
		if (!xmlFile.exists()) {
			throw new IllegalArgumentException("file not exsist");
		}

		if (xsdFile != null) {
			try {
				XmlValidator.checkXmlFile(xsdFile, xmlFile);
			} catch (final SAXException e) {
				throw new IllegalArgumentException("invalide xml file:" + xmlFile.getName(), e);
			} catch (final IOException e) {
				throw new IllegalArgumentException("invalide xml file:" + xmlFile.getName(), e);
			}
		}

		return parse(parseDocument(xmlFile));
	}

	@SuppressWarnings("unchecked")
	private <T> T parse(final Document doc) {
		// 这个map是用来存放每个节点和其对应的java类对象的.注意key是首字母大写，其余小写的，即类名风格
		final Map<Element, Object> elementObjectMap = new HashMap<>();

		// 广度优先遍历xml的dom树
		final Queue<Element> elements = new LinkedList<>();

		final Element root = doc.getRootElement();
		elements.add(root);
		while (!elements.isEmpty()) {
			final Element element = elements.poll();
			parseNode(elementObjectMap, element);
			elements.addAll(element.elements());
		}

		return (T) elementObjectMap.get(root);
	}

	/**
	 * 解析一个节点，生成它的对应java bean实例，并放入map中。
	 * 
	 * @param elementObjectMap
	 * @param node
	 */
	protected void parseNode(final Map<Element, Object> elementObjectMap, final Element node) {
		final String nodeName = node.getName();
		if (nodeName == null) {
			// 根据dom4j的注释文档，CDATA元素和文本元素（比如注释）是没有nodeName的。暂时先不处理这些
		} else {
			final Object object = createObjectForNode(nodeName);

			elementObjectMap.put(node, object);

			// 把节点的attribute放到节点对象里
			putAttributeToObject(node, object);

			//把节点的内容放到节点对象里
			putContentToObject(node, object);

			// 放到父节点的对象里面去
			putObjectToParent(elementObjectMap, node, object);
		}
	}

	protected void putContentToObject(final Element node, final Object object) {
		final String content = node.getText();
		final Method method = MethodUtil.findMethodIgnoreCaseAndArgsTypesAssigned(object.getClass().getMethods(),//
				"set", String.class);
		if (method != null) {
			MethodUtil.invokeMethod(object, method, content);
		}
	}

	/**
	 * @param elementObjectMap
	 * @param node
	 * @param object
	 */
	protected void putObjectToParent(final Map<Element, Object> elementObjectMap, final Element node, final Object object) {
		final Element parentElement = node.getParent();
		if (parentElement == null) {// root，也有可能是一些其他情况。详见dom4j文档
			// do nothing
		} else {
			final Object parent = elementObjectMap.get(parentElement);
			if (parent == null) {
				// 理论上不会到这里
			} else {
				ClassUtil.setField(parent, object);
			}
		}
	}

	/**
	 * @param node
	 * @param object
	 */
	@SuppressWarnings("unchecked")
	protected void putAttributeToObject(final Element node, final Object object) {
		final List<Attribute> attributes = node.attributes();
		for (final Attribute attribute : attributes) {
			final String attrName = attribute.getName();
			final String attrValue = attribute.getValue();
			final String attrClassName = nodeNameToClassName(attrName);
			Class<?> attrClass = ClassUtil.getClass(attrClassName);
			if (attrClass == null) {
				attrClass = String.class;
			}
			final Method method = MethodUtil.findMethodIgnoreCaseAndArgsTypesAssigned(object.getClass().getMethods(),//
					BeanUtil.fieldNameToSetterName(attrName), attrClass);
			if (method != null) {
				MethodUtil.invokeMethod(object, method, attrValue);
			}
		}
	}

	/**
	 * @param nodeName
	 * @return
	 */
	protected Object createObjectForNode(final String nodeName) throws IllegalArgumentException {
		// 根据配置中设定的class的名字反射一个节点对象出来。
		final String className = nodeNameToClassName(nodeName);
		if (Kits.STR.isEmpty(className)) {
			throw new IllegalArgumentException("node " + nodeName + " has no class mapped");
		}

		return ClassUtil.newInstance(className);
	}

	protected Document parseDocument(final File xmlFile) throws IllegalArgumentException {
		final SAXReader reader = new SAXReader();
		try {
			return reader.read(xmlFile);
		} catch (final DocumentException e) {
			throw new IllegalArgumentException(e);
		}
	}

	protected Document parseDocument(final InputStream inputStream) throws IllegalArgumentException {
		final SAXReader saxReader = new SAXReader();
		try {
			return saxReader.read(inputStream);
		} catch (final DocumentException e) {
			throw new IllegalArgumentException(e);
		}
	}

	protected Document parseDocument(final Reader reader) throws IllegalArgumentException {
		final SAXReader saxReader = new SAXReader();
		try {
			return saxReader.read(reader);
		} catch (final DocumentException e) {
			throw new IllegalArgumentException(e);
		}
	}

	protected String nodeNameToClassName(final String nodeName) {
		return Character.toUpperCase(nodeName.charAt(0)) + nodeName.substring(1);
	}

}

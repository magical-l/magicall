package me.magicall.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

/**
 * Validate the deep configuration file format according to associated XML
 * schema.
 * 
 * @author guangfeng
 * @author wenjian.liang
 */
public class XmlValidator {
	/**
	 * Check the configuration file.
	 * 
	 * @param schemaFile
	 * @param xmlFile
	 * @return True if the configuration file format is correct; otherwise false. exceptions caught and logged as the caller did before.
	 * @throws IOException
	 * @throws SAXException
	 */
	public static void checkXmlFile(final String schemaFile, final String xmlFile) throws SAXException, IOException {
		checkXmlFile(schemaFile, new File(xmlFile));
	}

	public static void checkXmlFile(final String schemaFile, final File xmlFile) throws SAXException, IOException {
		checkXmlFile(new File(schemaFile), xmlFile);
	}

	public static void checkXmlFile(final File schemaFile, final File xmlFile) throws SAXException, IOException {
		final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		final Schema schema = schemaFactory.newSchema(schemaFile);
		final Validator validator = schema.newValidator();

		validator.validate(new StreamSource(xmlFile));

	}
}

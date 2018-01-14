package com.basic;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;

public class TestXmlXXE {
	private static final Logger log = LoggerFactory.getLogger(TestXmlXXE.class);

	@Test(expected = SAXParseException.class)
	public void testReadXmlSecure() throws Exception {
		readXmlSecure("src/test/resources/xml/person_scan.xml");
	}

	@Test(expected = SAXParseException.class)
	public void testReadXmlSecure2() throws Exception {
		readXmlSecure("src/test/resources/xml/person_xml2.xml");
	}

	@Test
	public void testReadXmlUnSecure() throws Exception {
		readXmlUnSecure("src/test/resources/xml/person_scan.xml");
	}

	@Test
	public void testReadXmlUnSecure_2() throws Exception {
		readXmlUnSecure("src/test/resources/xml/person_xml2.xml");
	}

	private void readXmlUnSecure(String fileName) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		// documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING,
		// false);
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new File(fileName));
		doc.getDocumentElement().normalize();
		log.info("root element={}", doc.getDocumentElement().getNodeName());
		readXmlPerson(doc);
	}

	/**
	 * 安全读取xml
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	private void readXmlSecure(String fileName) throws Exception {
		String feature = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		feature = "http://apache.org/xml/features/disallow-doctype-decl";
		dbFactory.setFeature(feature, true);
		feature = "http://xml.org/sax/features/external-general-entities";
		dbFactory.setFeature(feature, false);
		feature = "http://xml.org/sax/features/external-parameter-entities";
		dbFactory.setFeature(feature, false);
		feature = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
		dbFactory.setFeature(feature, false);
		dbFactory.setXIncludeAware(false);
		dbFactory.setExpandEntityReferences(false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new File(fileName));
		doc.getDocumentElement().normalize();
		log.info("root element={}", doc.getDocumentElement().getNodeName());
		readXmlPerson(doc);
	}

	private void readXmlPerson(Document doc) {
		NodeList nList = doc.getElementsByTagName("person");
		Node node = null;
		Element eElement = null;
		for (int temp = 0; temp < nList.getLength(); temp++) {
			node = nList.item(temp);
			log.info("current element={}", node.getNodeName());
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				eElement = (Element) node;
				log.info("id={} ", eElement.getAttribute("id"));
				log.info("name={}", eElement.getElementsByTagName("name").item(0).getTextContent());
				log.info("address={}", eElement.getElementsByTagName("address").item(0).getTextContent());
			}
		}
	}
}

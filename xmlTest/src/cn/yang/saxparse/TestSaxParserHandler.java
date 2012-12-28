package cn.yang.saxparse;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class TestSaxParserHandler {

	XMLReader reader;

	@Before
	public void Setup() throws Exception {
		//
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser sp = factory.newSAXParser();
		this.reader = sp.getXMLReader();

	}

	@Test
	public void readXMLElements() throws Exception {
		// 读取出XML所有节点及内容
		this.reader.setContentHandler(new ListHandler());
		reader.parse("src/book.xml");
	}
	@Test
	public void findTagValue() throws Exception{
		//查询某个节点的值
		this.reader.setContentHandler(new TagValue());
		this.reader.parse("src/book.xml");
	}
}
class TagValue extends DefaultHandler {
	
	private String currentTag;//记住当前解析的是什么标签
	private int needNumber=1;//记住想获取第几个作者的标签的值
	private int currentNumber;//当前解析到的是第几个 
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		this.currentTag=qName;//先保存当前读取到的标签名称
		if(this.currentTag.equals("作者"))
			currentNumber++;
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if("作者".equals(this.currentTag)&&this.currentNumber==this.needNumber)
			System.out.println("result: "+new String(ch,start,length));
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		this.currentTag=null;//在读取到标签后面时,将状态值currentTag 设置为null
	}
}
class ListHandler implements ContentHandler {

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {

		System.out.print("startElement running...");
		System.out.print("uri:" + uri);
		System.out.print(" localName:" + localName);
		System.out.print(" name:" + name);
		printAttributes(attributes);
		System.out.println();
	}

	private void printAttributes(Attributes attributes) {
		for (int i = 0; attributes != null && i < attributes.getLength(); i++) {
			String QName = attributes.getQName(i);
			String attValue = attributes.getValue(i);
			System.out.println(QName + "=" + attValue);
		}
	}

	@Override
	public void characters(char[] ch, int start, int lenght)
			throws SAXException {
		System.out.print(" characters running...");
		System.out.print(" character: ");
		System.out.println(new String(ch, start, lenght));
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		System.out.print("endElement running...");
		System.out.print("uri:" + uri);
		System.out.print(" localName:" + localName);
		System.out.print(" name:" + name);//localName 节点名称
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("endDocument running...");
	}

	@Override
	public void endPrefixMapping(String arg0) throws SAXException {
		System.out.println("endPrefixMapping running...");
	}

	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
			throws SAXException {
		System.out.println("ignorableWhitespace running...");
	}

	@Override
	public void processingInstruction(String arg0, String arg1)
			throws SAXException {

		System.out.println("processingInstruction running...");
	}

	@Override
	public void setDocumentLocator(Locator arg0) {

		System.out.println("setDocumentLocator running...");
	}

	@Override
	public void skippedEntity(String arg0) throws SAXException {

		System.out.println("skippedEntity running...");
	}

	@Override
	public void startDocument() throws SAXException {

		System.out.println("startDocument running...");
	}

	@Override
	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {

		System.out.println("startPrefixMapping running...");
	}

}

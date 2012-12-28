package cn.yang.xmlparse;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParse {

	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	Document document;
	@Before
	public void setUp() throws Exception {
		//创建工厂
		factory =DocumentBuilderFactory.newInstance();
		//得到dom解析器
		builder =factory.newDocumentBuilder();
		//解析xml文档，得到代表文档的document
		document=builder.parse("src/book.xml");
		
	}

	/**读取xml文档中：<书名>JavaScript网页开发</书名>*/
	@Test
	public void testReadElementContent() throws Exception{
		NodeList list =document.getElementsByTagName("书名");
		Node node =list.item(1);
		System.out.println(node.getTextContent());
	}
	/**打印出所有节点名称*/
	@Test
	public void testReadAllElementsName() throws Exception{
		//得到根节点
		Node root =document.getElementsByTagName("书架").item(0);
		list(root);
	}

	/**一个递归方法，用于遍历节点下的子节点*/
	private void list(Node node) {
		if(node instanceof Element)
			System.out.println(node.getNodeName());
		NodeList list =node.getChildNodes();
		for (int i=0;i<list.getLength();i++) {
			Node child=list.item(i);
			list(child);
		}
	}

	
	/**获取xml文档中标签属性的值：<书名 name="name">Java就业培训教程</书名>*/
	@Test 
	public void testReadAttribute()throws Exception{
		//得到元素
		Element bookname=(Element)document.getElementsByTagName("作者").item(0);
		String value="";
		if(bookname.hasAttribute("name"))
			value=bookname.getAttribute("name");
		
		System.out.println("value: "+value);
	}
	
	/**向xml文档中增加节点<售价>59.00元</售价>*/
	@Test
	public void testAppendElement() throws Exception{
		//创建节点
		Element price =
				document.createElement("售价");
		price.setTextContent("79.00元");
		//把创建的节点拷到第一本书上
		Element book =
				(Element)document.getElementsByTagName("书").item(0);
		book.appendChild(price);
		Node newNode =book.getElementsByTagName("售价").item(1);
		System.out.println(newNode.getNodeName()+newNode.getTextContent());
		//更新xml文档
		refreshXML(document,"src/book.xml");
	}

	/**在某个节点前插入一个新节点*/
	@Test
	public void testInsertElementBeforeOne() throws Exception {
		//创建节点
		Element newChild =document.createElement("售价");
		newChild.setTextContent("99.00元");
		//得到参考节点
		Element refChild =(Element)document.getElementsByTagName("售价").item(0);
		//得到父节点
		Element book =
				(Element)document.getElementsByTagName("书").item(0);
		//在父节点上的指定位置插入新的节点
		book.insertBefore(newChild, refChild);
		//更新xml文档
		refreshXML(document,"src/book.xml");
	}
	/**添加节点的属性*/
	@Test //<书名 name="your name" >Java就业培训教程</书名>
	public void testAddAttribute() throws Exception{
		Element bookname =
				(Element)document.getElementsByTagName("书名").item(0);
		bookname.setAttribute("name","your name");
		//更新xml文档
		refreshXML(document,"src/book.xml");
	}
	/**删除节点的属性*/
	@Test
	public void testRemoveAttribute() throws Exception{
		Element bookname =
				(Element)document.getElementsByTagName("书名").item(0);
		bookname.removeAttribute("name");
		//更新xml文档
		refreshXML(document,"src/book.xml");
	}
	/**更新节点的属性，与设置属性方法相同*/
	@Test
	public void testUpdataAttribute() throws Exception{
		Element bookname =
				(Element)document.getElementsByTagName("书名").item(0);
		bookname.setAttribute("name","your name new ");
		//更新xml文档
		refreshXML(document,"src/book.xml");
	}
	/**删除节点*/
	@Test
	public void testDeleteElement() throws Exception{
		//指定要删除的节点
		Element deleteElement =(Element) document.getElementsByTagName("售价").item(0);
		//再获取其父节点
		Node parent =deleteElement.getParentNode();
		//之后删除
		parent.removeChild(deleteElement);
		//更新xml文档
		refreshXML(document,"src/book.xml");
	}
	/**更新节点内容*/
	@Test
	public void testUpdataElementTextContent() throws Exception{
		Element updataElement =
				(Element)document.getElementsByTagName("售价").item(0);
		updataElement.setTextContent("59.00元");
		//更新xml文档
		refreshXML(document,"src/book.xml");
	}
	/**从document中更新XML文档*/
	private void refreshXML(Document documentSource, String xmlFileName) throws Exception{
		TransformerFactory tfFactory=
				TransformerFactory.newInstance();
		Transformer tf =tfFactory.newTransformer();
		tf.transform(new DOMSource(documentSource),
				new StreamResult(new File(xmlFileName)));
		
	}
	
	
}

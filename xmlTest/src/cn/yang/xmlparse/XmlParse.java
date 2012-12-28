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
		//��������
		factory =DocumentBuilderFactory.newInstance();
		//�õ�dom������
		builder =factory.newDocumentBuilder();
		//����xml�ĵ����õ������ĵ���document
		document=builder.parse("src/book.xml");
		
	}

	/**��ȡxml�ĵ��У�<����>JavaScript��ҳ����</����>*/
	@Test
	public void testReadElementContent() throws Exception{
		NodeList list =document.getElementsByTagName("����");
		Node node =list.item(1);
		System.out.println(node.getTextContent());
	}
	/**��ӡ�����нڵ�����*/
	@Test
	public void testReadAllElementsName() throws Exception{
		//�õ����ڵ�
		Node root =document.getElementsByTagName("���").item(0);
		list(root);
	}

	/**һ���ݹ鷽�������ڱ����ڵ��µ��ӽڵ�*/
	private void list(Node node) {
		if(node instanceof Element)
			System.out.println(node.getNodeName());
		NodeList list =node.getChildNodes();
		for (int i=0;i<list.getLength();i++) {
			Node child=list.item(i);
			list(child);
		}
	}

	
	/**��ȡxml�ĵ��б�ǩ���Ե�ֵ��<���� name="name">Java��ҵ��ѵ�̳�</����>*/
	@Test 
	public void testReadAttribute()throws Exception{
		//�õ�Ԫ��
		Element bookname=(Element)document.getElementsByTagName("����").item(0);
		String value="";
		if(bookname.hasAttribute("name"))
			value=bookname.getAttribute("name");
		
		System.out.println("value: "+value);
	}
	
	/**��xml�ĵ������ӽڵ�<�ۼ�>59.00Ԫ</�ۼ�>*/
	@Test
	public void testAppendElement() throws Exception{
		//�����ڵ�
		Element price =
				document.createElement("�ۼ�");
		price.setTextContent("79.00Ԫ");
		//�Ѵ����Ľڵ㿽����һ������
		Element book =
				(Element)document.getElementsByTagName("��").item(0);
		book.appendChild(price);
		Node newNode =book.getElementsByTagName("�ۼ�").item(1);
		System.out.println(newNode.getNodeName()+newNode.getTextContent());
		//����xml�ĵ�
		refreshXML(document,"src/book.xml");
	}

	/**��ĳ���ڵ�ǰ����һ���½ڵ�*/
	@Test
	public void testInsertElementBeforeOne() throws Exception {
		//�����ڵ�
		Element newChild =document.createElement("�ۼ�");
		newChild.setTextContent("99.00Ԫ");
		//�õ��ο��ڵ�
		Element refChild =(Element)document.getElementsByTagName("�ۼ�").item(0);
		//�õ����ڵ�
		Element book =
				(Element)document.getElementsByTagName("��").item(0);
		//�ڸ��ڵ��ϵ�ָ��λ�ò����µĽڵ�
		book.insertBefore(newChild, refChild);
		//����xml�ĵ�
		refreshXML(document,"src/book.xml");
	}
	/**��ӽڵ������*/
	@Test //<���� name="your name" >Java��ҵ��ѵ�̳�</����>
	public void testAddAttribute() throws Exception{
		Element bookname =
				(Element)document.getElementsByTagName("����").item(0);
		bookname.setAttribute("name","your name");
		//����xml�ĵ�
		refreshXML(document,"src/book.xml");
	}
	/**ɾ���ڵ������*/
	@Test
	public void testRemoveAttribute() throws Exception{
		Element bookname =
				(Element)document.getElementsByTagName("����").item(0);
		bookname.removeAttribute("name");
		//����xml�ĵ�
		refreshXML(document,"src/book.xml");
	}
	/**���½ڵ�����ԣ����������Է�����ͬ*/
	@Test
	public void testUpdataAttribute() throws Exception{
		Element bookname =
				(Element)document.getElementsByTagName("����").item(0);
		bookname.setAttribute("name","your name new ");
		//����xml�ĵ�
		refreshXML(document,"src/book.xml");
	}
	/**ɾ���ڵ�*/
	@Test
	public void testDeleteElement() throws Exception{
		//ָ��Ҫɾ���Ľڵ�
		Element deleteElement =(Element) document.getElementsByTagName("�ۼ�").item(0);
		//�ٻ�ȡ�丸�ڵ�
		Node parent =deleteElement.getParentNode();
		//֮��ɾ��
		parent.removeChild(deleteElement);
		//����xml�ĵ�
		refreshXML(document,"src/book.xml");
	}
	/**���½ڵ�����*/
	@Test
	public void testUpdataElementTextContent() throws Exception{
		Element updataElement =
				(Element)document.getElementsByTagName("�ۼ�").item(0);
		updataElement.setTextContent("59.00Ԫ");
		//����xml�ĵ�
		refreshXML(document,"src/book.xml");
	}
	/**��document�и���XML�ĵ�*/
	private void refreshXML(Document documentSource, String xmlFileName) throws Exception{
		TransformerFactory tfFactory=
				TransformerFactory.newInstance();
		Transformer tf =tfFactory.newTransformer();
		tf.transform(new DOMSource(documentSource),
				new StreamResult(new File(xmlFileName)));
		
	}
	
	
}

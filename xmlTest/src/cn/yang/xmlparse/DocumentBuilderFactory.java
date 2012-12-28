package cn.yang.xmlparse;

public abstract class DocumentBuilderFactory {

	protected DocumentBuilderFactory() {
		
	}
	public static DocumentBuilderFactory newInstance(){
		return new DocumentBuilderFactory() {
		};
	}
}

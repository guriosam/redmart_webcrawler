package redmart_crawler;

public class Main {

	public static void main(String[] args) {
		Visitor red = new Visitor();
		try {
			red.setUp();
			red.getInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

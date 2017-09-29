package ie.gmit.sw;

public class Runner {

	//O(1)
	public static void main(String[] args) {
		
		/*long start = System.currentTimeMillis();
		
		PortaCipher c= new PortaCipher();
		c.setKeyword("DATASTRUCTURESANDALGORITHMSDATASTR");
		
		FileParser p=new FileParser(c);
		p.startEncryption("wap3.txt", "encwap.txt");
		
		System.out.println("Parsing finished in: " + (System.currentTimeMillis() - start) +" ms");
		*/
		
		//Create the menu
		Menu m =new Menu();
		//Draw the menu
		m.drawMenu();
	}
	
	
	
}

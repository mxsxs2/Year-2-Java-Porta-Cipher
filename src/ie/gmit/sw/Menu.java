package ie.gmit.sw;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
	//The input scanner
	private Scanner input;
	//The destination file name
	private String detsFileName;
	//The cipher
	private final Cipher cipher;
	
	/**
	 * The menu constructor
	 */
	//O(1)
	public Menu(){
		//Set the cipher
		this.cipher = new PortaCipher();
	}
	
	/***
	 * Function used to draw the menu and execute the chosen menu item
	 */
	//I am not sure what the running time would this be. Since the loop runs until the user exits. I guess its O(n) n=number of menu changes
	public void drawMenu(){
		//Run the application
		boolean alive=true;

		//Show the menu until its needed
		while(alive){
			//Draw the menu
			switch(this.drawMainMenu()){
				case 1:
					//Get the keyword and set in cipher
					this.cipher.setKeyword(this.getKeyword());
					break;
				case 2:
					//Get the destination file name
					this.getDestFileName();
					break;
				case 3:
					//Create the parser and get the source
					this.encrypt(this.getSourceUrl(),new UrlParser(cipher));
					break;
				case 4:
					//Create the parser and get the source
					this.encrypt(this.getSourceFileName(), new FileParser(cipher));
					break;
				case 5:
					//Encrypt input string
					this.encryptString();
					break;
				case -1:
					//Exit the application
					System.exit(0);
					break;
			}
		}
	}
	
	//O(1)
	private void encryptString(){
		//Check if the keyword is set
		if(this.cipher.getKeyword()==null || this.cipher.getKeyword().length()==0){
			System.out.println("\nThe keyword has to be set first\n");
			return;
		}
		
		System.out.println("\nPlease enter the text for encryption:");
		//Reset the scanner
		this.input=new Scanner(System.in);
		//Get the next line, encrypt it and output it
		System.out.println(this.cipher.encryptString(this.input.nextLine()));
	}
	
	/**
	 * Function used to check for the Prerequisites
	 * @return boolean
	 */
	//O(1)
	private boolean checkPrerequisites(){
		//Check if the keyword is set
		if(this.cipher.getKeyword()==null || this.cipher.getKeyword().length()==0){
			System.out.println("\nThe keyword has to be set first\n");
			return false;
		}
		
		//Check if the destination file is set
		if(detsFileName==null || this.detsFileName.length()==0){
			System.out.println("\nThe destination file has to be set first\n");
			return false;
		}
		
		return true;
	}
	
	/***
	 * Function used to start parsing from a source with a parser
	 * @param from - file path/url
	 * @param with - a parser
	 */
	//O(1)
	private void encrypt(String from, Parser with){
		
		if(!this.checkPrerequisites()) return;
		
		System.out.println("Please wait..");
		
		//Start the timer
		long start = System.currentTimeMillis();
		//Start parsing
		if(!with.startEncryption(from, this.detsFileName)){
			//Write out the error message if the parsing did not work
			System.out.println(with.getErrorMessage());
		}else{
			//Write out the parsing time
			System.out.println("Parsing finished in: " + (System.currentTimeMillis() - start) +" ms");
		}
		
	}
	
	
	
	/**
	 * Function used to draw the menu and return the user input
	 * @return int - menu
	 */
	//O(n)<-same guess as previously
	private int drawMainMenu(){
		//The menu option
		int option=0;
		
			//Show the options
			System.out.println("\n\n1. Add keyword to encryption");
			System.out.println("2. Add destionation file name");
			System.out.println("3. Encrypt url");
			System.out.println("4. Encrypt file");
			System.out.println("5. Encrypt text");
			System.out.println("-1. Exit");
			
			
			//Keep asking until a valid input is given
			while(option==0){
				//Keep asking until the value is valid
				System.out.println("Please input the menu number:");
				try{
					//Set the scanner
					this.input=new Scanner(System.in);
					//Read the int
					option=this.input.nextInt();
					//Return if it is valid
					if((option>0 || option==-1) && option<6) return option;
				}catch(InputMismatchException e){
					
				}
				//Reset if it is invalid
				option=0;
			}
		//Return exit
		return option;
	}
	
	/***
	 * Function used to get the keyword from the user
	 * @return String - keyword
	 */
	//O(1)
	private String getKeyword(){
			System.out.println("\nPlease enter the keyword:");
			//Reset the scanner
			this.input=new Scanner(System.in);
			//Get the next line
			return this.input.nextLine();
	}
	
	/***
	 * Function used to get the destination file name from the user
	 * @return void
	 */
	//O(1)
	private void getDestFileName(){
			System.out.println("\nPlease enter the destination file name:");
			//Reset the scanner
			this.input=new Scanner(System.in);
			//Set the destination file name
			this.detsFileName=this.input.nextLine();
	}
	
	/***
	 * Function used to get the source file name from the user
	 * @return String - source file
	 */
	//O(1)
	private String getSourceFileName(){
			System.out.println("\nPlease enter the source file name:");
			//Reset the scanner
			this.input=new Scanner(System.in);
			//Return the input
			return this.input.nextLine();
		
	}
	
	/***
	 * Function used to get the source url from the user
	 * @return String - source url
	 */
	//O(1)
	private String getSourceUrl(){
			System.out.println("\nPlease enter the source url:");
			//Reset the scanner
			this.input=new Scanner(System.in);
			//Return the input
			return this.input.nextLine();
		
	}

}

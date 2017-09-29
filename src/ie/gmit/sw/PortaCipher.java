package ie.gmit.sw;

public class PortaCipher implements Cipher{

	/*
	  The following tableau is used by the Porta Cipher:
		
	  Keys| A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
	  ---------------------------------------------------------
	  A,B | N O P Q R S T U V W X Y Z A B C D E F G H I J K L M
	  C,D | O P Q R S T U V W X Y Z N M A B C D E F G H I J K L
	  E,F | P Q R S T U V W X Y Z N O L M A B C D E F G H I J K 
	  G,H | Q R S T U V W X Y Z N O P K L M A B C D E F G H I J
	  I,J | R S T U V W X Y Z N O P Q J K L M A B C D E F G H I
	  K,L | S T U V W X Y Z N O P Q R I J K L M A B C D E F G H
	  M,N | T U V W X Y Z N O P Q R S H I J K L M A B C D E F G
	  O,P | U V W X Y Z N O P Q R S T G H I J K L M A B C D E F
	  Q,R | V W X Y Z N O P Q R S T U F G H I J K L M A B C D E
	  S,T | W X Y Z N O P Q R S T U V E F G H I J K L M A B C D
	  U,V | X Y Z N O P Q R S T U V W D E F G H I J K L M A B C
	  W,X | Y Z N O P Q R S T U V W X C D E F G H I J K L M A B
	  Y,Z | Z N O P Q R S T U V W X Y B C D E F G H I J K L M A
	
	
	  keyword: DATASTRUCTURESANDALGORITHMS
	  Plain Text: THECURFEWTOLLSTHEKNELLOFPARTINGDAY  
	  
	  (1) Repeat the keyword above the plaintex
		DATASTRUCTURESANDALGORITHMSDATASTR
		THECURFEWTOLLSTHEKNELLOFPARTINGDAY
		
	  (2) For each character in the plaintext
		
			Find the character at the intersection of the row
			containing the key character and the column containing the 
			plaintext.
		    
			K: DATASTRUCTURESANDALGORITHMSDATASTR
			P: THECURFEWTOLLSTHEKNELLOFPARTINGDAY
			   ----------------------------------
			C: FUNPLINOIKETNJGNSXIUSTKOMTIFVETZWD
		
	The encryption and decryption processes are identical. Encrypting 
	a piece of text twice with the same key will return the original text:
		
		K: DATASTRUCTURESANDALGORITHMSDATASTR
		C: FUNPLINOIKETNJGNSXIUSTKOMTIFVETZWD
		   ----------------------------------
		P: THECURFEWTOLLSTHEKNELLOFPARTINGDAY
	*/
	
	//The declared Keyword
	private String keyword;
	
	/**
	 * Function used to set a keyword
	 * @param keyword
	 */
	//O(1)
	public void setKeyword(String keyword){
		//Convert the input string to upper case and filter unwanted characters
		this.keyword=keyword.toUpperCase().replaceAll("[^A-Z]", "");
	}
	
	/**
	 * Function used to get the keyword
	 * @return String - keyword
	 */
	//O(1)
	public String getKeyword(){
		return this.keyword;
	}
	
		
	/**
	 * Function used to encrypt a single line text
	 * @param text
	 * @return String
	 */
	//O(n)
	public String encryptString(final String text){
			//clean the text
			StringBuilder newS = new StringBuilder(text.toUpperCase().replaceAll("[^A-Z]", ""));
				//Iterate through the input characters
				for(int k=0; k<newS.length(); k++){
						//This line gets the actual character and the value to shift with.
						//Shifts the character and writes back into its original position
					    newS.setCharAt(k,(char)this.encryptChar(text.charAt(k),k));
				}
	        //Return the encrypted string
	       	return newS.toString();
		}
	
	/**
	 * Function used to encrypt a character by a loop index
	 * @param Char
	 * @param index
	 * @return int
	 */
	//O(1)
	public int encryptChar(int Char,int index){  
		//Return the new line back
		if(Char==10) return 10;
		//Convert to upper case
		if(Char>96 && Char<123) Char=java.lang.Character.toUpperCase(Char);
		//Return the encrypted character
		return this.shiftChar(Char,this.getCharShiftValue(index));
	}
	/**
	 * Function used to get the a character from the keyword by the loops index	
	 * @param i - loops index
	 * @return short
	 */
	//O(1)
	private short getCharShiftValue(int i){
		//Get the keyword character's position and the character
		int c=this.keyword.charAt(i%this.keyword.length());
		//Get the index of the key char for each row e.g. AB=>1 CD=>2 etc...
		if(c<67) return 0;
		if(c<69) return 1;
		if(c<71) return 2;
		if(c<73) return 3;
		if(c<75) return 4;
		if(c<77) return 5;
		if(c<79) return 6;
		if(c<81) return 7;
		if(c<83) return 8;
		if(c<85) return 9;
		if(c<87) return 10;
		if(c<89) return 11;
		
		//When the character is higher than 88
		return 12;
	}

	/**
	 * Function used to shift a character (get the encrypted value) 
	 * @param charInt
	 * @param keyCharInt
	 * @return int
	 */
	//O(1)
	private int shiftChar(int charInt, int keyCharInt){
			//If char is M then the first index higher with 13
			if(charInt<=77 && charInt>=65){
				int num=charInt+13+keyCharInt;
				return num>90 ? num-13 : num;
			//If the char is after M then the first index is lower with 13
			}else if(charInt<=90 && charInt>=78){
				int num=charInt-13-keyCharInt;
				return num<65 ? num+13 : num;
			}
		//never happens
		return -1;
		
	}
}
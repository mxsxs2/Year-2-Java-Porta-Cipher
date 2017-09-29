package ie.gmit.sw;

public interface Cipher {
	
	/**
	 * Function used to set a keyword
	 * @param keyword
	 */
	public void setKeyword(String keyword);
	
	/**
	 * Function used to get the keyword
	 * @return String - keyword
	 */
	public String getKeyword();
	
	/**
	 * Function used to encrypt a single line text
	 * @param text
	 * @return String
	 */
	public String encryptString(final String text);
	
	/**
	 * Function used to encrypt a character by a loop index
	 * @param Char
	 * @param index
	 * @return int
	 */
	public int encryptChar(int Char,int index);
}

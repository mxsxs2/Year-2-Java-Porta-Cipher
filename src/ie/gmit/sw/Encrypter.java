package ie.gmit.sw;

public interface Encrypter {
	
	/**
	 * Function used to encrypt a source to a file
	 * @param from - source
	 * @param to - destination file
	 * @return boolean
	 */
    public boolean startEncryption(String from, String to);
	
}

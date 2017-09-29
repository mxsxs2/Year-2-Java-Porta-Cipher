/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.gmit.sw;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author user
 */
public abstract class Parser implements Parseable, Encrypter{
    //The cipher
    protected Cipher cipher;
    //The name of the new file
    protected String FileName;
    
    //Container for parsing error messages
    private String errorMessage;
    
    //Encoding types
    private String[] encoding={
    		"UTF-8",
			"UTF-16",
			"ISO-8859-1",
			"GB2312",
			"Windows-1251",
			"Windows-1252",
			"Shift JIS",
			"GBK",
			"Windows-1256",
			"ISO-8859-2",
			"EUC-JP",
			"ISO-8859-15",
			"ISO-8859-9",
			"Windows-1250",
			"Windows-1254",
			"EUC-KR",
			"Big5",
			"Windows-874",
			"US-ASCII",
			"TIS-620",
			"ISO-8859-7",
			"Windows-1255"};
    
   
    /***
     * Constructor with cipher
     * @param c - Cipher
     */
  //O(1)
    public Parser(Cipher c){
    	this.setCipher(c);
    }
    
    
    @Override
  //O(1)
    public String getErrorMessage(){
        return this.errorMessage;
    }
    
    /**
     * Function used to set the error message
     * @param errorMessage
     */
  //O(1)
    protected void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	/**
     * The function returns the character encodings
     * @return String[]
     */
  //O(1)
    public String[] getEncodings(){
    	return this.encoding;
    }
    
   
    /**
     * Function used to set the cipher
     * @param chiper - PortaCipher
     */
  //O(1)
    public void setCipher(Cipher cipher){
    	this.cipher=cipher;
    }
    /**
     * Function used to set the destination file
     * @param name - String
     */
  //O(1)
    public void setFileName(String name){
    	this.FileName=name;
    }


  
 
    /**
     * Function used to encrypt an input stream and output it into a file
     * Running time on war and piece is ~480ms
     * @param inputStream
     */
  //O(n) n=number of characters in the file
    public void convert(InputStream inputStream){
   		try {
			//Buffer the input stream to 2^20	
			BufferedInputStream inputStreamReader = new BufferedInputStream(inputStream,1048576);
			//Open an output stream and set a buffer to on it
			BufferedOutputStream outputStreamWriter = new BufferedOutputStream(new FileOutputStream(this.FileName),1048576);
			//Read the first byte
	    	int data = inputStreamReader.read();
	    	//Index of read and valid bytes
	    	int i=0;
	    	//Read until there is something to read
	    	while(data != -1){
	    		//Filter to lower and upper case and space 
	    	    if((data>64 && data<91) || (data>96 && data<123) || data==10){
	    	    	//Encrypt the character and write out
	    	    	outputStreamWriter.write(this.cipher.encryptChar(data,i));
	    	    	//Increment
	    	    	i++;
	    	    }
	    	    //Read nex character
	    	    data = inputStreamReader.read();
	    	}
	    	//Close the streams
	    	inputStreamReader.close();
	    	outputStreamWriter.close();
		} catch (IOException e) {
			//The source is checked before
			//Set error message for destination file
			this.setErrorMessage("Could not write to the destination file.");
		}
    }    
}

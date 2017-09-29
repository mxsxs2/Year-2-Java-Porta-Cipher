/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.gmit.sw;


import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 *
 * @author user
 */
public class FileParser extends Parser{
    


	//variable for the file 4Bytes
    private File parsedFile;
    //variable for the buffered reader 4Bytes
    private BufferedReader br;
    
    //O(1)
    public FileParser(Cipher c) {
		super(c);
	}
    
    
    @Override
  //O(1)
    public void setSource(Object Object) {
        //Check if it is a file 
        if(Object instanceof File){
            //Cast it save it
            this.parsedFile = (File)Object;
        }else{
            //if not a file then cast to string and create a file
            this.parsedFile = new File((String)Object);
        }   
    }

    
    
    @Override
    //O(1)
    public boolean availableSource() {
        //Check if the file exists and if it is actually a file
        if(this.parsedFile.exists() && this.parsedFile.isFile() && this.parsedFile.canRead()) return true;
        //Set error message
        this.setErrorMessage("The source file does not exists or cannot read it.");
        //If not the return false;
        return false;
    }
   
    
    @Override
    //O(1)
    public boolean startEncryption(String from, String to){
    	//Set the input
    	this.setSource(from);
    	//Set the output
    	this.setFileName(to);
    	//Check if the file is available
        if(this.availableSource()){  
        		try {
        			//Call the convert method of the parent class this ~480ms on war and peace
					super.convert(new FileInputStream(this.parsedFile.toString()));
					
					return true;
				} catch (FileNotFoundException e) {
					//It is checked for earlier
				}
        		//Call the java 8 version of converter. this is~620ms on war and peace
				//Left it here just for comparison
				//this.convert();
        }
        return false;
    }
    
    /**
     * Auto detect the encoding of the file
     * @param charsetIndex
     */
  //O(1)
    protected void decodeFile(int charsetIndex) throws IOException{
    	//Declare the encodings to try
    	String[] enc=this.getEncodings(); 	
    	try{
    		//Create a buffered reader with a given charset
    		BufferedReader br=Files.newBufferedReader(this.parsedFile.toPath(), Charset.forName(enc[charsetIndex]));
    		br.mark(1);
    		//Try to read the first char
    		br.read();
    		//Reset the reader
    		br.reset();
    		this.br=br;
    		//If the line could be read then "rewind" the buffered reader and return it
        	//this.br=Files.newBufferedReader(this.parsedFile.toPath(), Charset.forName(enc[charsetIndex]));
        	
    	}catch(MalformedInputException ex){
    		//If an exception was thrown (for example because of the wrong charset)
    		//Check if there is any more charset is available for testing
    		if(enc.length>++charsetIndex){
    			//Try to decode again with a different encoding
        		this.decodeFile(charsetIndex);
        	}
    	}
    }
    
     
    /*
     * File encryption function which implements java 8 streams 
     * Running time on war and peace is ~620ms   
     */
    @SuppressWarnings("unused")
    //O(n)
	private void convert(){
		try {
			//Try to open the file in buffered reader
			this.decodeFile(0);	
			//Open an output stream and set a buffer to on it
			BufferedOutputStream outputStreamWriter = new BufferedOutputStream(new FileOutputStream(this.FileName),1048576);
			//Need an index incrementer
			AtomicInteger index = new AtomicInteger();
			//Get the lines
			this.br.lines()
			  //Flat map them to int and add a line break on the end
			  .flatMapToInt(n->IntStream.concat(n.chars(),Arrays.stream(new int[]{10})))
			  //Convert to upper case
			  .map(Character::toUpperCase)
			  //Filter the characters
			  .filter(c -> ((c>=65 && c<=90) || ((c>96 && c<123)) || c==10))
			  //Replace the characters
			  .map(data->data=this.cipher.encryptChar(data,index.getAndIncrement()))
			  //Stream the data
			  .forEach((data)->{
				try {
					//Write it out
					outputStreamWriter.write(data);
				} catch (IOException e) {
					//Set error message for destination file
					this.setErrorMessage("Could not write to the destination file.");
				}
			});
			//Close the output stream
	    	outputStreamWriter.close();
		} catch (IOException e) {
			//Set error message for destination file
			this.setErrorMessage("Could not write to the destination file.");
		}
    }
}

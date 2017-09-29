/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.gmit.sw;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author user
 */
public class UrlParser extends Parser{
    

	//Url
    private URL url;
  //O(1)
    public UrlParser(Cipher c) {
		super(c);
	}


    /**
     * Function used to add http:// to the url if it is missing
     * @param url 
     * @return string
     */
  //O(1)
    private String checkHttpProtocol(String url){
    	if(!url.contains("http://")) url="http://"+url;
    	return url;
    }
    
    @Override
  //O(1)
    public void setSource(Object o) {
        try {
            //Set the url form sting
        	this.url=new URL(this.checkHttpProtocol((String)o));
        } catch (MalformedURLException ex) {
            //Do nothing as available source will return false anyways.
        }
    }

    
    @Override
  //O(1)
    public boolean availableSource() {
        //If the url is not set the return false;
       if(this.url==null) return false;
        try {
            //Get connection for url
            HttpURLConnection conn = this.openConnection("HEAD");
           //If the http request code is 200 the the url is readable. otherwise we leave it.
           
            //if the response was not 200 then return true;
            if(200==conn.getResponseCode()) return true;
            
            //If the response code was not 200
            this.setErrorMessage("The server responded: "+conn.getResponseCode()+","+conn.getResponseMessage()+" (read more at: https://en.wikipedia.org/wiki/List_of_HTTP_status_codes)");
            
        } catch (IOException ex) {
            //If an exception was thrown
        	this.setErrorMessage("The url cannot be resolved.");
        }
        //it is not available
        return false;
    }

     @Override
   //O(1)
    public boolean startEncryption(String from, String to){
    	//Set the input
    	this.setSource(from);
    	//Set the output
    	this.setFileName(to);
    	
    	//Return false if the source is not available
    	if(!this.availableSource()) return false;
    	
    	try{
            //Get connection for url
            HttpURLConnection conn = this.openConnection("GET");
            //Get the stream
            InputStream is = conn.getInputStream();
                //Check if the content is gzipped
            if("gzip".equals(conn.getContentEncoding())){
                    //Create a new gzip stream 
                    is = new java.util.zip.GZIPInputStream(is);
            }
            super.convert(is);
            return true;    
        }catch (IOException ex) {
                //Do nothing the file url be found anyways. We check this before.
                
        }
        return false;
    }  
  
    /**
     * Opens a new Http connection to the given url
     * @param request -String. The request method
     * @return HttpURLConnection - The opened connection
     * @throws IOException 
     */
   //O(1)
    private HttpURLConnection openConnection(String request) throws IOException{
        //Create the connection
        HttpURLConnection conn= (HttpURLConnection)this.url.openConnection ();
        //Set header so we wont get 403
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36");
        //Set the request to head
        conn.setRequestMethod(request);
        
        return conn;
    }
}

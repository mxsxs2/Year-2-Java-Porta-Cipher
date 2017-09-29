/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.gmit.sw;


/**
 *
 * @author user
 */
public interface Parseable{
     /**
     * Sets the source file 
     * @param Object type of Object 
     */
    public void setSource(Object Object);
    
    /**
     * Check if the source is available
     * @return boolean
     */
    public boolean availableSource();
    
    /**
     * Get source error message
     * @return String
     */
    public String getErrorMessage();
    
    /**
     * Starts the conversion from one file to another
     * @param from
     * @param to
     * @return boolean
     */
    public boolean startEncryption(String from, String to);
}

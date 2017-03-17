/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

/**
 *
 * @author rqz5104
 */
public class CourseInfo {
    
    private int intID;
    private int intNumber;
    private boolean blnWriting;
    private int intCredits;
    private String strTitle;
    private String strDescription;
    
    public CourseInfo(int intID, int intNumber, boolean blnWriting){
        
        this.intID = intID;
        this.intNumber = intNumber;
        this.blnWriting = blnWriting;
        
    }
    
    @Override
    public String toString(){
        return String.valueOf(getIntNumber()) + (isBlnWriting() ? "W" : "");
    }

    /**
     * @return the intID
     */
    public int getIntID() {
        return intID;
    }

    /**
     * @param intID the intID to set
     */
    public void setIntID(int intID) {
        this.intID = intID;
    }

    /**
     * @return the intNumber
     */
    public int getIntNumber() {
        return intNumber;
    }

    /**
     * @param intNumber the intNumber to set
     */
    public void setIntNumber(int intNumber) {
        this.intNumber = intNumber;
    }

    /**
     * @return the blnWriting
     */
    public boolean isBlnWriting() {
        return blnWriting;
    }

    /**
     * @param blnWriting the blnWriting to set
     */
    public void setBlnWriting(boolean blnWriting) {
        this.blnWriting = blnWriting;
    }

    /**
     * @return the intCredits
     */
    public int getIntCredits() {
        return intCredits;
    }

    /**
     * @param intCredits the intCredits to set
     */
    public void setIntCredits(int intCredits) {
        this.intCredits = intCredits;
    }

    /**
     * @return the strTitle
     */
    public String getStrTitle() {
        return strTitle;
    }

    /**
     * @param strTitle the strTitle to set
     */
    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    /**
     * @return the strDescription
     */
    public String getStrDescription() {
        return strDescription;
    }

    /**
     * @param strDescription the strDescription to set
     */
    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }
    
}

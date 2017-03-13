/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

/**
 *
 * @author Robert Zwolinski
 */
public class ClassInfo {
    
    private int intID;
    private int intSection;
    
    public ClassInfo (int intIDIn, int intSectionIn){
        intID = intIDIn;
        intSection = intSectionIn;
    }
    
    public String toString(){
        return String.valueOf(getIntSection());
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
     * @return the intSection
     */
    public int getIntSection() {
        return intSection;
    }

    /**
     * @param intSection the intSection to set
     */
    public void setIntSection(int intSection) {
        this.intSection = intSection;
    }
    
}

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
public enum Campus {
    OZ ("Abington"),
    AB ("Abington"),
    AA ("Altoona"),
    AL ("Altoona"),
    BR ("Beaver"),
    BK ("Berks"),
    DE ("Brandywine"),
    BW ("Brandywine"),
    DN ("Carlisle"),
    CR ("Carlisle"),
    DS ("DuBois"),
    BD ("Erie"),
    ER ("Erie"),
    FE ("Fayette"),
    KP ("Great Valley"),
    GV ("Great Valley"),
    MK ("Greater Allegheny"),
    GA ("Greater Allegheny"),
    CL ("Harrisburg"),
    HB ("Harrisburg"),
    HN ("Hazleton"),
    HY ("Hershey"),
    AN ("Lehigh Valley"),
    LV ("Lehigh Valley"),
    MA ("Mont Alto"),
    NK ("New Kensington"),
    SL ("Schuylkill"),
    SV ("Shenango"),
    SH ("Shenango"),
    UP ("University Park"),
    WB ("Wilkes-Barre"),
    WD ("World Campus"),
    WC ("World Campus"),
    WS ("Worthington Scranton"),
    YK ("York");
    
    private final String strName; // Campus name
    
    Campus (String strName){
        this.strName = strName;
    }
    
    public String getName(){
        return strName;
    }
    
}

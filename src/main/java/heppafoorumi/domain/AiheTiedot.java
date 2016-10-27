/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heppafoorumi.domain;

import java.sql.Timestamp;

/**
 *
 * @author zille
 */
public class AiheTiedot {
    
    public final Aihe aihe;
    public final Viesti viesti;
    
    public AiheTiedot (Aihe aihe, Viesti viesti) {
        this.aihe = aihe;
        this.viesti = viesti;
    }
    
    public String getOtsikko() {
        return this.aihe.getOtsikko();
    }
    
    public String getKuvaus() {
        return this.aihe.getKuvaus();
    }
    
    public Timestamp getTimestamp() {
        return this.viesti.getAikaleima();
    }
    
    public int getId() {
        return this.aihe.getId();
    }
    
    
}

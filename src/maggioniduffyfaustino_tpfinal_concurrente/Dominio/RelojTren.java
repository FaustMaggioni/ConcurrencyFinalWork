/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggioniduffyfaustino_tpfinal_concurrente;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Faustino
 */
public class RelojTren implements Runnable {
    private boolean lleno=false;
    private ControlTren tren;

    public RelojTren(ControlTren tren) {
        this.tren = tren;
    }
    
    
    public void run(){
        try {
            Thread.sleep(40000);
            tren.largarTren();
        } catch (InterruptedException ex) {
            Logger.getLogger(RelojTren.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setLleno(){
        lleno=true;
    }
    
}

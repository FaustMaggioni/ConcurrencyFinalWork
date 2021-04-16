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
public class RelojAeropuerto implements Runnable {
    
    private Aeropuerto apuerto;

    public RelojAeropuerto(Aeropuerto apuerto) {
        this.apuerto = apuerto;
    }
    
    
    
    public void run(){
        while(true){
            try {
                Thread.sleep(32000);
                apuerto.cerradura();
                Thread.sleep(16000);
                apuerto.cerradura();
            } catch (InterruptedException ex) {
                Logger.getLogger(RelojAeropuerto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

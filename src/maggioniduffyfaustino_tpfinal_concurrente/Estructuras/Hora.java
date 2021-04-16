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
public class Hora implements Runnable {
    private Aeropuerto apuerto;

    public Hora(Aeropuerto apuerto) {
        this.apuerto = apuerto;
    }
    
    public void run(){
       while(true){
           try {
               Thread.sleep(2000);
               apuerto.pasarHora();
           } catch (InterruptedException ex) {
               Logger.getLogger(Hora.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
    
}

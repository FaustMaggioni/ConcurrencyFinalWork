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
public class HiloViaje implements Runnable {
    private Viaje viaje;
    private int hora;
    private int horaInicio;
    
    
    public HiloViaje(Viaje v){
        viaje=v;
    }

    public HiloViaje(Viaje viaje, int hora, int horaInicio) {
        this.viaje = viaje;
        this.hora = hora;
        this.horaInicio = horaInicio;
    }

    
    
    public void run(){
        try {
            int segs= (hora-horaInicio)*1000;
            Thread.sleep(segs*2);
            viaje.despegar();
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloViaje.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

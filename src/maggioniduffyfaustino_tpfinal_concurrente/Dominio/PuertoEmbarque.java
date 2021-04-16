/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggioniduffyfaustino_tpfinal_concurrente;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Faustino
 */
public class PuertoEmbarque {
    private Lock abordar= new ReentrantLock();
    
    public void embarcar(Viaje v) throws InterruptedException{
        v.viajar();
        System.out.println("Pasajero se va de viaje.");
        System.out.println("FIN!");
    }
}

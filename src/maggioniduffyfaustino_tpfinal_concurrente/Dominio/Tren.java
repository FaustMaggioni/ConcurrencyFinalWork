/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggioniduffyfaustino_tpfinal_concurrente;

/**
 *
 * @author Faustino
 */
public class Tren implements Runnable {
    private ControlTren ct;

    public Tren(ControlTren ct) {
        this.ct = ct;
    }
    
    public void run(){
        while(true){
            ct.comenzarRecorrido();
        }
    }
    
}

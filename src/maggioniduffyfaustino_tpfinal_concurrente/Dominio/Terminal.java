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
public class Terminal {
    private PuertoEmbarque[] puertos;
    private FreeShop fshop;
    private int id;
    // tienen que tener el doble de tiempo restante a viajar que lo que tarda 
    // la visita promedio al free shop por si tienen que esperar o compran algo.
    public PuertoEmbarque getUnPuerto(){
        return puertos[Math.round((float) (Math.random()*(puertos.length-1)))];
    }

    public Terminal(int id) {
        this.puertos = new PuertoEmbarque[id+1];
        for(int i=0;i<puertos.length;i++){
           puertos[i]= new PuertoEmbarque();
        }
        this.fshop = new FreeShop(30000,15,2);
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }
    
    public void esperarEmbarque(int id, PuertoEmbarque destino,Viaje v, int horaInicial) throws InterruptedException{
        System.out.println("ESPERANDO EMBARQUE PASAJERO "+id);
        int tiempoParaElVuelo= v.getHora()-horaInicial;
        int tiempoFreeShop= fshop.getTiempo();
        if(2*tiempoParaElVuelo < tiempoFreeShop && Math.random()<0.5){
            try {
                System.out.println("----- PASAJERO "+id+" VA AL ****** FREESHOP ******");
                this.irAlFreeShop( id, tiempoParaElVuelo, tiempoFreeShop,destino,v);
            } catch (InterruptedException ex) {
                Logger.getLogger(Terminal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            this.esperarVuelo(id,destino,v);
        }
    }
    private void irAlFreeShop(int id, int tiempoParaVuelo, int tiempoFreeShop, PuertoEmbarque destino,Viaje v) throws InterruptedException{
        fshop.entrarPorFreeShop(id);
        Thread.sleep(tiempoFreeShop);
        if(Math.random()<0.35){
            fshop.pagarEnCaja(id);
            Thread.sleep(tiempoFreeShop/5);
            fshop.terminarPago(id);
        }
        fshop.irseDelFreeShop(id);
        this.esperarVuelo(id,destino,v);
    }
    
    private void esperarVuelo(int id,PuertoEmbarque destino,Viaje v) throws InterruptedException{
        System.out.println("---------- PASAJERO "+id+"VA A ESPERAR EL VUELO");       
        destino.embarcar(v);
        System.out.println("++++++ PASAJERO "+id+" EMBARCA SU VUELO \n .... BUEN VIAJE!");
        
    }
    public PuertoEmbarque[] getPuertos() {
        return puertos;
    }

    public void setPuertos(PuertoEmbarque[] puertos) {
        this.puertos = puertos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ""+id;
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Terminal other = (Terminal) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}

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
public class Pasajero implements Runnable {
    private int id;
    private Reserva rsv;
    private Aeropuerto apuerto;
    private CheckIn ch;

    public Pasajero(int id, Aeropuerto apuerto) {
        this.id = id;
        this.apuerto = apuerto;
    }
    
    
    
    public void run(){
        try {
            apuerto.puestoDeInformesYReserva(id, this);
        } catch (InterruptedException ex) {
            Logger.getLogger(Pasajero.class.getName()).log(Level.SEVERE, null, ex);
        }
        PuestoAtencion aerolinea= this.rsv.getAerolinea();
        aerolinea.esperarParaCheckIn(id);
        this.hacerCheckIn(id);
        aerolinea.terminarAtencion(id);
        Terminal ter= ch.getTerminal();
        apuerto.subirAlTren(this);
        try {
            ter.esperarEmbarque(id, ch.getPuerto(),rsv.getViaje(),apuerto.getHora());
        } catch (InterruptedException ex) {
            Logger.getLogger(Pasajero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void enLaTerminal(){
        
    }
    public CheckIn getCh() {
        return ch;
    }

    public void setCh(CheckIn ch) {
        this.ch = ch;
    }
    public void hacerCheckIn(int id){
        Terminal aux= apuerto.getUnaTerminal();
        PuertoEmbarque pemb= aux.getUnPuerto();
        System.out.println("------------------- Pasajero "+id+" haciendo el CHECK-IN.....");
        try {
            CheckIn chin= new CheckIn(aux,pemb);
            this.setCh(chin);
            Thread.sleep((long) (Math.random()*5000));
        } catch (InterruptedException ex) {
            Logger.getLogger(PuestoAtencion.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(" CHECK IN LISTO PARA PASAJERO:::::: "+id);
    }
    
    public Pasajero(int id, Reserva rsv) {
        this.id = id;
        this.rsv = rsv;
    }

    public Pasajero(int id, Reserva rsv, Aeropuerto aereo) {
        this.id = id;
        this.rsv = rsv;
        this.apuerto = aereo;
    }

    public Aeropuerto getAereo() {
        return apuerto;
    }

    public void setAereo(Aeropuerto aereo) {
        this.apuerto = aereo;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reserva getRsv() {
        return rsv;
    }

    public void setRsv(Reserva rsv) {
        this.rsv = rsv;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggioniduffyfaustino_tpfinal_concurrente;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Aeropuerto {
    private PuestoInformes informes= new PuestoInformes();
    private PuestoAtencion[] aerolineas;
    private Terminal[]terminales;
    private String color= Colores.ANSI_BLUE;
    private ControlTren tren;
    private boolean pasar=true;
    private Lock open= new ReentrantLock();
    private Condition abierto= open.newCondition();
    private int horaActual = 6;

    public Aeropuerto(PuestoAtencion[] aerolineas, Terminal[] terminales) {
        this.aerolineas = aerolineas;
        this.terminales = terminales;
    }

    public Aeropuerto(PuestoAtencion[] aerolineas, Terminal[] terminales, ControlTren tren) {
        this.aerolineas = aerolineas;
        this.terminales = terminales;
        this.tren = tren;
    }
    
    
    
    public synchronized void pasarHora(){
        if(horaActual>=23){
            horaActual=0;
        }else{
            horaActual++;
        }
    }
    public void puestoDeInformesYReserva(int id,Pasajero pas) throws InterruptedException{
        open.lock();
        while(!pasar){
            abierto.await();
        }
        open.unlock();
        this.serAtendidoEnPuesto(id, pas);
    }
    
    public synchronized int getHora(){
        return this.horaActual;
    }

    private void serAtendidoEnPuesto(int id, Pasajero pas) throws InterruptedException{
            System.out.println(color+" ------- Pasajero nro "+id+" LLEGA AL AEROPUERTO ------");
            informes.serAtendido(id);
            Reserva aux=this.generarReserva(pas);
            pas.setRsv(aux);
    }
    public void cerradura(){
        open.lock();
        pasar=!pasar;
        abierto.signalAll();
        open.unlock();
    }
    private Reserva generarReserva(Pasajero pas){
        PuestoAtencion aeroline= this.getUnaAerolinea();
        Vuelo vuelo= aeroline.getUnVuelo();
        Viaje hora= vuelo.getUnaHora(getHora());
        Reserva aux= new Reserva(vuelo,hora,aeroline);
        return aux;
    }
    public void subirAlTren(Pasajero pas){
        tren.subirseAlTren("", pas);
    }
    private PuestoAtencion getUnaAerolinea(){
        return aerolineas[Math.round((float) (Math.random()*(aerolineas.length-1)))];
    }
    
    public Terminal getUnaTerminal(){
        return terminales[Math.round((float) (Math.random()*(terminales.length-1)))];
    }
}

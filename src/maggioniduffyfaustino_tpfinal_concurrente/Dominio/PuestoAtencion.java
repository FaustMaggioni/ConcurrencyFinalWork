/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggioniduffyfaustino_tpfinal_concurrente;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Faustino
 */
public class PuestoAtencion {
    private Vuelo[] vuelos= new Vuelo[10];
    private String nombre;
    private Aeropuerto apuerto;
    private final LinkedBlockingQueue orden= new LinkedBlockingQueue();
    private String color= Colores.ANSI_GREEN;
    private int cantMax;
    private int cantActual;
    private int genteEnElHall=0;
    private Semaphore mutex= new Semaphore(1);
    private Semaphore salaDeEspera= new Semaphore(0);
    private Semaphore hall= new Semaphore(1);
    private Lock esperar= new ReentrantLock();
    private Condition sala= esperar.newCondition();
    private int idActual=0;

    public PuestoAtencion( String nombre, Aeropuerto apuerto, int cantMax,int horaInicial) {
        this.nombre = nombre;
        this.apuerto = apuerto;
        this.cantMax = cantMax;
        this.cantActual = 0;
        
        for(int i=0;i<vuelos.length;i++){
            Viaje[]horas= new Viaje[5+i];
            for(int x=0;x<horas.length;x++){
                horas[x]= Viaje((int) (Math.round(Math.random()*23)),horaInicial);
            }
            vuelos[i]= new Vuelo(horas,(i+1)*100);
        }
    }
    
    
    public Vuelo getUnVuelo(){
        return vuelos[Math.round((float) (Math.random()*(vuelos.length-1)))];
    }
    public void esperarParaCheckIn(int id){
        try {
            System.out.println(color+"+++++ PASAJERO "+id+" llega al puesto de atención");          
            mutex.acquire();
            if(cantActual>=cantMax){
                mutex.release();
                this.hallDeEspera(id);
            }
            else{
                mutex.release();
                this.continuarEspera(id);
            }
            
        } catch (InterruptedException ex) {
            Logger.getLogger(PuestoAtencion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private synchronized boolean compId(int id){
        return id==idActual;
    }
    private synchronized void colaVacia(int id){
        if(orden.isEmpty()){
        idActual=id;
        }
        orden.add(id);
    }
    private void continuarEspera(int id) throws InterruptedException{
        mutex.acquire();
        cantActual++;
        mutex.release();
        esperar.lock();
        System.out.println(color+"+++++ PASAJERO "+id+" pasa a la sala de espera del puesto de atención");
        this.colaVacia(id);
        while(!compId(id)){                        
            sala.await();
        }  
        this.entraPasajero();         
        esperar.unlock();
        //hace check in en la clase pasajero
    }
    private synchronized int getIdActual(){
        return this.idActual;
    }
    private synchronized void entraPasajero() throws InterruptedException{
        int aux= (int)orden.poll();
        if(orden.isEmpty()){
            this.idActual=aux;
        }else{
            this.idActual= (int) orden.peek();
        }
        hall.acquire();
        if(this.genteEnElHall>0){
            this.salaDeEspera.release();
        }
        hall.release();
    }
    public void terminarAtencion(int id){
        esperar.lock();
        sala.signalAll();
        System.out.println("Pasajero "+id+" se fue despues de hacer el checkin.");
        esperar.unlock();
    }
    /*private synchronized void dejarCama(){
        this.camaOcupadas--;
        System.out.println(this.camaOcupadas);
    }*/
    private void hallDeEspera(int id){
        try {
            hall.acquire();
            this.genteEnElHall++;
            hall.release();
            System.out.println("Pasajero "+id+" en el hall de espera....");
            salaDeEspera.acquire();
            hall.acquire();
            this.genteEnElHall--;
            hall.release();
            this.continuarEspera(id);
        } catch (InterruptedException ex) {
            Logger.getLogger(PuestoAtencion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Viaje Viaje(int i, int horaInicial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

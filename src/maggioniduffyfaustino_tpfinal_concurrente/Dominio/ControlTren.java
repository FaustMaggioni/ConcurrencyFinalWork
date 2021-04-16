/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggioniduffyfaustino_tpfinal_concurrente;

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
public class ControlTren {
    private Semaphore asientos;
    private Semaphore arrancar = new Semaphore(0, true);
    private Semaphore mutex = new Semaphore(1, true);
    private int cant;
    private Contador contador= new Contador();
    private int recorridos;
    private Terminal[]terminales;
    private Terminal siguiente;
    private Lock bajar= new ReentrantLock();
    private Condition[] miTerminal;
    private Thread reloj;
    private boolean arranco=false;

    public ControlTren(int cant, Terminal[] terminales) {
        this.cant = cant;
        this.terminales = terminales;
        asientos = new Semaphore(cant, true);
        recorridos= terminales.length-1;
        siguiente= null;
        miTerminal= new Condition[recorridos+1];
        for(int i=0;i<=recorridos;i++){
            miTerminal[i]= bajar.newCondition();
        }
    }
    
    public void subirseAlTren(String color, Pasajero pas){
        try {
            asientos.acquire();
            System.out.println(color+" pasajero"+pas.getId()+" sube al tren.");
            contador.sumar();
            if(contador.getI()==this.cant){
                arrancar.release();
                contador.setI(0);
            }
            bajar.lock();
            while(!getArranco()){
                int i= pas.getCh().getTerminal().getId();
                try {
                    System.out.println("Pasajero "+pas.getId()+" va a bajar en la terminal "+i);
                    miTerminal[i].await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ControlTren.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            bajar.unlock();
            System.out.println("--------------- PASAJERO "+pas.getId()+" se BAJA DEL TREN....... ");
        } catch (InterruptedException ex) {
            Logger.getLogger(ControlTren.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private boolean getArranco(){
        boolean res;
        try {
            mutex.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(ControlTren.class.getName()).log(Level.SEVERE, null, ex);
        }
        res= this.arranco;
        mutex.release();
        return res;          
    }
    private void aTerminales() throws InterruptedException{
        for (int i=0;i<=this.recorridos;i++){
            System.out.println("TREN YENDO A TERMINAL "+i);
            Thread.sleep(10000);
            System.out.println("TREN LLEGA A TERMINAL "+i);
            bajar.lock();
            miTerminal[i].signalAll();
            bajar.unlock();          
        }
    }
    
    public void comenzarRecorrido(){
        
        try {
            arranco=false;
            System.out.println(" __________________________________________________________________________  TREN EMPIEZA A LLENARSE");
            reloj= new Thread(new RelojTren(this));
            this.reloj.start();
            arrancar.acquire();
            arranco=true;
            this.aTerminales();           
            Thread.sleep(10000);// Tiempo de regreso
            asientos.release(cant);
        } catch (InterruptedException ex) {
            Logger.getLogger(ControlTren.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void largarTren(){
        if(!getArranco()){
            try {
                int aux= contador.getI();
                arrancar.release();
                asientos.acquire(cant-aux);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControlTren.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    
    
}

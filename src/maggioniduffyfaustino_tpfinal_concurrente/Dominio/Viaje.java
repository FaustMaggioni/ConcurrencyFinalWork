/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggioniduffyfaustino_tpfinal_concurrente;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Faustino
 */
public class Viaje{
    private int hora;
    private int horaInicial;
    private Lock sale = new ReentrantLock();
    private Condition salir = sale.newCondition();
    private boolean go=false;
    private Thread hilo;
    
    public Viaje(int hora, int horaInicial) {
        this.hora = hora;
        this.horaInicial = horaInicial;
        hilo = new Thread(new HiloViaje(this,hora,horaInicial));
        hilo.start();
    }
    
    public void viajar() throws InterruptedException{
        sale.lock();
        while(!getGo()){
            salir.await();
        }
        sale.unlock();
    }
    
    public void despegar(){
        sale.lock();
        go=true;
        salir.signalAll();
        sale.unlock();
    }
    
    public boolean getGo(){
        return this.go;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(int horaInicial) {
        this.horaInicial = horaInicial;
    }
    
    
    
    
}

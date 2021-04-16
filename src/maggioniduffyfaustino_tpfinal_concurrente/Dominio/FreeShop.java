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
public class FreeShop {
    private int tiempo;
    private int cantMax;
    private int cantActual=0;
    private Lock entrada= new ReentrantLock();
    private Condition hayLugar= entrada.newCondition();
    private Lock caja= new ReentrantLock();
    private Condition cajaLibre= caja.newCondition();
    private int cantCajas;
    private int cajasOcupadas;
    private Lock mutex= new ReentrantLock();

    public FreeShop(int tiempo, int cantMax, int cantCajas) {
        this.tiempo = tiempo;
        this.cantMax = cantMax; //2
        this.cantCajas = cantCajas;
    }
    
    
    public void pagarEnCaja(int id) throws InterruptedException{
        caja.lock();
        System.out.println("_____________________ PASAJERO "+id+" quiere comprar algo");
        while(!cajaLibre()){
            cajaLibre.await();
        }
        mutex.lock();
        System.out.println("_____________________ PASAJERO "+id+" es atendido en CAjA");
        this.cajasOcupadas++;
        mutex.unlock();
        caja.unlock();
    }
    public void terminarPago(int id){
        mutex.lock();
        this.cajasOcupadas--;
        mutex.unlock();
        caja.lock();
        cajaLibre.signal();
        System.out.println("_____________________ PASAJERO "+id+" termina EL PAGO------- ");
        caja.unlock();
    }
    
    public boolean cajaLibre(){
        boolean res;
        mutex.lock();
        res= this.cajasOcupadas < this.cantCajas;
        mutex.unlock();
        return res;
    }
            
    
    public void entrarPorFreeShop(int id) throws InterruptedException{
        System.out.println("PASAJERO "+id+" ESPERA ENTRAR AL ***FREESHOP***");
        entrada.lock();
        while(!this.compararLugar()){
            hayLugar.await();
        }
        this.sumar();
        System.out.println("_____________________ PASAJERO "+id+" ENTRA AL ***FREESHOP***");
        entrada.unlock();
    }
    public void irseDelFreeShop(int id){
        entrada.lock();
        this.restar();
        System.out.println("_____________________ PASAJERO "+id+" SE VA DEL ***FREESHOP***");
        hayLugar.signal();
        entrada.unlock();
    }
    public int getTiempo() {
        return tiempo;
    }
    public synchronized void sumar(){
        this.cantActual++;
    }
    public synchronized void restar(){
        this.cantActual--;
    }
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
    
    public synchronized boolean compararLugar(){
        return this.cantMax > this.cantActual;
    }
    public synchronized int getCantMax() {
        return cantMax;
    }

    public synchronized void setCantMax(int cantMax) {
        this.cantMax = cantMax;
    }

    public synchronized int getCantActual() {
        return cantActual;
    }

    public synchronized void setCantActual(int cantActual) {
        this.cantActual = cantActual;
    }
    
    
}

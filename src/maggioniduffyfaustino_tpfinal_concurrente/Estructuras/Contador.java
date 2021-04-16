/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggioniduffyfaustino_tpfinal_concurrente;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Faustino
 */
public class Contador {
    private int i;
    ReentrantLock us= new ReentrantLock(true);
    public Contador() {
        this.i = 0;
    }

    public int getI() {
        us.lock();
        try {
            return i;
        } finally {
            us.unlock();
        }
    }

    public void setI(int i) {
        this.i = i;
    }
    public  void sumar(){
        us.lock();
        this.i++;
        us.unlock();
    }
    
    public void restar(){
        us.lock();
        this.i--;
        us.unlock();
    }
}

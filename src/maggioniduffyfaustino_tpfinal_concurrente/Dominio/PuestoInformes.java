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
public class PuestoInformes {
    private String color= Colores.ANSI_RED;
    private ReentrantLock atencion= new ReentrantLock(true);
    
    public void serAtendido(int id) throws InterruptedException{
        System.out.println(color+" Pasajero "+id+" esperando ingresar al puesto de informes....");
        atencion.lock();
        System.out.println(color+" PUESTO DE INFORMES ATENDIENDO A PASAJERO "+id);
        Thread.sleep((long) ((Math.random()+1000)*5));
        atencion.unlock();
        System.out.println(color+" Pasajero "+id+" SE DIRIGE A HACER EL ___CHECK IN___");
    }
}

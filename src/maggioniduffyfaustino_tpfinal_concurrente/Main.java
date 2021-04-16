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
public class Main {
    public static void main(String[] args) throws InterruptedException{
        Pasajero[]psjs= new Pasajero[20]; //hilos
        Terminal[]terminales= new Terminal[4];
        ControlTren ct= new ControlTren(7,terminales);
        Tren tren= new Tren(ct);
        PuestoAtencion[] aerolineas= new PuestoAtencion[5];
        Aeropuerto apuerto= new Aeropuerto(aerolineas,terminales,ct);
        int horaAeropuerto= apuerto.getHora();
        for(int i=0;i<aerolineas.length;i++){
            aerolineas[i]= new PuestoAtencion("Aerolinea "+i,apuerto,(i+5),horaAeropuerto);
        }
        for(int i=0;i<terminales.length;i++){
            terminales[i]= new Terminal(i);
        }
        RelojAeropuerto relojA= new RelojAeropuerto(apuerto); //hilos
        Thread[]hilos= new Thread[psjs.length+3];
        
        //-------------------------------------------------------------//
        
        for(int i=2;i<hilos.length;i++){
                hilos[i]= new Thread(new Pasajero(i,apuerto));
            }
        hilos[0]= new Thread(tren);
        hilos[1]= new Thread(relojA);
        hilos[2]= new Thread(new Hora(apuerto));
        for(int i=0;i<hilos.length;i++){
            if(i>=3)
                Thread.sleep((long) (Math.random()*5000+2000));
            hilos[i].start();
        }
    }
}

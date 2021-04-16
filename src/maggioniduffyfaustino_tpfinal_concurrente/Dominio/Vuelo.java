/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggioniduffyfaustino_tpfinal_concurrente;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Faustino
 */
public class Vuelo {
    private Viaje[]horas;
    private int id;

    public Vuelo(Viaje[] horas, int id) {
        this.horas = horas;
        this.id = id;
    }
    
    public Viaje getUnaHora(int horaMin){
        Viaje[]aux= this.filtrarHoras(horaMin);
        return aux[Math.round((float) (Math.random()*(aux.length-1)))];
    }
    
    private Viaje[] filtrarHoras(int h){
        int cant=0;
        List aux= new LinkedList();
        for(int i=0;i<horas.length;i++){
            if(horas[i].getHora()>=h+1){
                cant++;
                aux.add(horas[i]);
            }
        }
        Viaje[]res= new Viaje[cant];
        for(int j=0;j<=aux.size();j++){
            res[j]=(Viaje)aux.get(j);
        }
        return res;
    }
    
    
}

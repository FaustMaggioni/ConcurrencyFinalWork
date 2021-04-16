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
public class Reserva{
     private Vuelo vuelo;
     private Viaje hora;
     private PuestoAtencion aerolinea;
     
    public Reserva(Vuelo vuelo, Viaje hora, PuestoAtencion aerolinea) {
        this.vuelo = vuelo;
        this.hora = hora;
        this.aerolinea = aerolinea;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Viaje getViaje() {
        return hora;
    }

    public void setHora(Viaje hora) {
        this.hora = hora;
    }
    
    public PuestoAtencion getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(PuestoAtencion aerolinea) {
        this.aerolinea = aerolinea;
    }
    
    
     
     
}
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
public class CheckIn {
    private Terminal terminal;
    private PuertoEmbarque puerto;

    public CheckIn(Terminal terminal, PuertoEmbarque puerto) {
        this.terminal = terminal;
        this.puerto = puerto;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public PuertoEmbarque getPuerto() {
        return puerto;
    }

    public void setPuerto(PuertoEmbarque puerto) {
        this.puerto = puerto;
    }
    
    
}

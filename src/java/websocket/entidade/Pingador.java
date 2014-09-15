package websocket.entidade;

import java.util.ArrayList;
import java.util.List;
import util.ExceptionPersonalizada;

/**
 *
 * @author rubensmarcon
 */
public class Pingador extends Thread {
    
    private LocalMaquina localMaquina;
    
    public List<Pingador> inicializar() throws ExceptionPersonalizada {
        List<Pingador> listaPingadores = new ArrayList<>();
        for (LocalMaquina lm : LocalMaquina.listar()) {
            Pingador pingador = new Pingador();
            listaPingadores.add(pingador);
        }
        return listaPingadores;
    }
    
}

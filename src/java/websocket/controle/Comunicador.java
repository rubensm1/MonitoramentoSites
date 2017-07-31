package websocket.controle;

import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import websocket.entidade.Sistema;
import websocket.entidade.mensagem.Mensagem;
import websocket.entidade.mensagem.MensagemGrowl;

/**
 *
 * @author rubensmarcon
 */
public class Comunicador extends Thread {

    private Sistema sistema;

    public Comunicador(Sistema sistema) {
        this.sistema = sistema;
    }

    @Override
    public void run() {
        executar();
    }

    public void executar() {
        Navegador navegador;
        try {
            navegador = new Navegador(this.sistema.getUrl());
        } catch (MalformedURLException ex) {
            Logger.getLogger(Comunicador.class.getName()).log(Level.SEVERE, null, ex);
            PontoWebSocket.enviaObjeto(new Mensagem("ping", "", MensagemGrowl.mensagemErro(ex)));
            return;
        }
        try {
            while(this.sistema.isAtivo()) {
                synchronized(this.sistema){
                    Mensagem mensagem = new Mensagem("ping", navegador.pingLink(sistema.getId()), "");
                    PontoWebSocket.enviaObjeto(mensagem);
                    //System.out.println(mensagem);
                }
                this.sleep(5000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Comunicador.class.getName()).log(Level.SEVERE, null, ex);
            PontoWebSocket.enviaObjeto(new Mensagem("ping", "", MensagemGrowl.mensagemErro(ex)));
        }
    }

    public Sistema getLocalMaquina() {
        return sistema;
    }

    public void setLocalMaquina(Sistema sistema) {
        this.sistema = sistema;
    }
}

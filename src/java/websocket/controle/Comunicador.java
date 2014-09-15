package websocket.controle;

import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import websocket.entidade.LocalMaquina;
import websocket.entidade.StatusConexao;
import websocket.entidade.mensagem.Mensagem;
import websocket.entidade.mensagem.MensagemGrowl;

/**
 *
 * @author rubensmarcon
 */
public class Comunicador extends Thread {

    private LocalMaquina localMaquina;

    public Comunicador(LocalMaquina localMaquina) {
        this.localMaquina = localMaquina;
    }

    @Override
    public void run() {
        executar();
    }

    public void executar() {
        Navegador navegador;
        try {
            navegador = new Navegador(this.localMaquina.getUrl());
        } catch (MalformedURLException ex) {
            Logger.getLogger(Comunicador.class.getName()).log(Level.SEVERE, null, ex);
            PontoWebSocket.enviaObjeto(new Mensagem("ping", "", MensagemGrowl.mensagemErro(ex)));
            return;
        }
        try {
            while(this.localMaquina.isAtiva()) {
                synchronized(this.localMaquina){
                    Mensagem mensagem = new Mensagem("ping", navegador.ping(localMaquina.getId()), "");
                    PontoWebSocket.enviaObjeto(mensagem);
                }
                this.sleep(5000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Comunicador.class.getName()).log(Level.SEVERE, null, ex);
            PontoWebSocket.enviaObjeto(new Mensagem("ping", "", MensagemGrowl.mensagemErro(ex)));
        }
    }

    public LocalMaquina getLocalMaquina() {
        return localMaquina;
    }

    public void setLocalMaquina(LocalMaquina localMaquina) {
        this.localMaquina = localMaquina;
    }
}

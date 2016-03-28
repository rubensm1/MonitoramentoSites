package websocket.controle;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import websocket.entidade.Maquina;
import websocket.entidade.StatusConexao;
import websocket.entidade.mensagem.Mensagem;
import websocket.entidade.mensagem.MensagemGrowl;

/**
 *
 * @author rubensmarcon
 */
public class Pingador extends Thread {
    
    private final Maquina maquina;

    public Pingador(Maquina maquina) throws UnknownHostException {
        this.maquina = maquina;
        this.maquina.setNet(InetAddress.getByName(maquina.getEndereco()));
    }
    
    @Override
    public void run() {
        executar();
    }

    public void executar() {
        try {
            while(this.maquina.isAtiva()) {
                synchronized(this.maquina){
                    Mensagem mensagem = new Mensagem("ping", pingIp(), "");
                    PontoWebSocket.enviaObjeto(mensagem);
                }
                this.sleep(5000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Comunicador.class.getName()).log(Level.SEVERE, null, ex);
            PontoWebSocket.enviaObjeto(new Mensagem("ping", "", MensagemGrowl.mensagemErro(ex)));
        }
    }
    
     public StatusConexao pingIp() {
        if (this.maquina == null && this.maquina.getNet() != null)
            return new StatusConexao(maquina.getId(), -1, "Maquina Indefinida!");
        try {
            long delay = new Date().getTime();
            boolean resposta = this.maquina.getNet().isReachable(15000);
            delay = new Date().getTime() - delay;
            if (resposta)
                return new StatusConexao(maquina.getId(), delay, "OK");
            else
                return new StatusConexao(maquina.getId(), delay, "Limite timeout excedido!");
        } catch (IOException ex) {
            Logger.getLogger(Navegador.class.getName()).log(Level.SEVERE, null, ex);
            return new StatusConexao(maquina.getId(), -1, "Falha na Conexão!");
        }
    }
    /*public List<Pingador> inicializar() throws ExceptionPersonalizada {
        List<Pingador> listaPingadores = new ArrayList<>();
        for (LocalMaquina lm : ((List<LocalMaquina>) ConexaoEntityManager.listar(LocalMaquina.class))) {
            Pingador pingador = new Pingador();
            listaPingadores.add(pingador);
        }
        return listaPingadores;
    }*/

    public Maquina getMaquina() {
        return maquina;
    }
     
}

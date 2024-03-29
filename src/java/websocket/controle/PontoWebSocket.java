package websocket.controle;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import websocket.entidade.Sistema;
import websocket.entidade.Maquina;
import websocket.entidade.mensagem.Mensagem;
import websocket.entidade.mensagem.MensagemGrowl;
import websocket.entidade.mensagem.encoder.MensagemDecoder;
import websocket.entidade.mensagem.encoder.MensagemEncoder;

/**
 *
 * @author rubensmarcon
 */
@ServerEndpoint(
        encoders = {MensagemEncoder.class},
        decoders = {MensagemDecoder.class},
        value = "/endpoint")
public class PontoWebSocket extends Endpoint {

    private static final Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    private static final Map<Integer, Comunicador> comunicadores = Collections.synchronizedMap(new HashMap<Integer, Comunicador>());
    private static final Map<Integer, Pingador> pingadores = Collections.synchronizedMap(new HashMap<Integer, Pingador>());

    @OnMessage
    public void onMessage(Mensagem mensagem, Session session) throws IOException, EncodeException {
        if (peers.isEmpty()) {

        }
        switch (mensagem.getTipo()) {
            case "start":

                break;
            case "conect":
                Sistema sistema = (Sistema) mensagem.getDados();
                if (sistema.isAtivo()) {
                    if (comunicadores.get(sistema.getId()) == null) {
                        Comunicador comunicador = new Comunicador(sistema);
                        comunicadores.put(sistema.getId(), comunicador);
                        comunicador.start();
                    }
                } else {
                    Comunicador comunicador = comunicadores.get(sistema.getId());
                    if (comunicador != null) {
                        synchronized(comunicador.getLocalMaquina()){
                            comunicador.getLocalMaquina().setAtivo(false);
                        }
                        mensagem = new Mensagem("desat", comunicador.getLocalMaquina().getId(), "");
                        enviaObjeto(mensagem);
                        comunicadores.remove(sistema.getId());
                    }
                }
                break;
            case "conectMaquina":
                Maquina maquina = (Maquina) mensagem.getDados();
                if (maquina.isAtiva()) {
                    if (pingadores.get(maquina.getId()) == null) {
                        try {
                            Pingador pingador = new Pingador(maquina);
                            pingadores.put(maquina.getId(), pingador);
                            pingador.start();
                        } catch(UnknownHostException ex) {
                            Mensagem msg = new Mensagem("erro", "", MensagemGrowl.mensagemErro(ex));
                            session.getBasicRemote().sendObject(msg);
                        }
                    }
                } else {
                    Pingador pingador = pingadores.get(maquina.getId());
                    if (pingador != null) {
                        synchronized(pingador.getMaquina()){
                            pingador.getMaquina().setAtiva(false);
                        }
                        mensagem = new Mensagem("desat", pingador.getMaquina().getId(), "");
                        enviaObjeto(mensagem);
                        pingadores.remove(maquina.getId());
                    }
                }
                break;
            case "matar":
                //Server server = new Server("localhost", 8080, "/Monitoramento", PontoWebSocket.class);
                /*try {
                 server.start();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                 System.out.print("Please press a key to stop the server.");
                 reader.readLine();
                 } catch (Exception e) {
                 throw new RuntimeException(e);
                 } finally {
                 server.stop();
                 }*/
                //server.stop();
                finalizar();
                break;
        }

    }

    @OnOpen
    @Override
    public void onOpen(Session peer, EndpointConfig config) {
        peers.add(peer);
        /*Mensagem mensagem = new Mensagem("init", null, "");
        Integer[] ids = new Integer[comunicadores.size()];
        int i = 0;
        for (Comunicador comunicador : comunicadores.values()) {
            //comunicador.getLocalMaquina().setAtiva(false);
            ids[i] = comunicador.getLocalMaquina().getId();
            i++;
        }
        mensagem.setDados(ids);
        enviaObjetoRestrito(mensagem, peer);*/
    }

    @OnClose
    @Override
    public void onClose(Session peer, CloseReason closeReason) {
        peers.remove(peer);
        if (peers.isEmpty()) {
            desconectarComunicadores();
            desconectarPingadores();
        }
    }

    @OnError
    @Override
    public void onError(Session peer, Throwable t) {
        Logger.getLogger(PontoWebSocket.class.getName()).log(Level.SEVERE, null, t);
    }

    /**
     * Envia uma mensagem para os browsers conectados
     *
     * @param mensagem
     */
    public static void enviaObjeto(Mensagem mensagem) {
        for (Session session : peers) {
            enviaObjetoRestrito(mensagem, session);
        }
    }

    public synchronized static void enviaObjetoRestrito(Mensagem mensagem, Session session) {
        try {
            session.getBasicRemote().sendObject(mensagem);
            //System.out.println(mensagem);
        } catch (IOException | EncodeException ex) {
            Logger.getLogger(PontoWebSocket.class.getName()).log(Level.SEVERE, null, ex);
            mensagem.setDados("");
            mensagem.setErro(MensagemGrowl.mensagemErro(ex));
            try {
                session.getBasicRemote().sendObject(mensagem);
            } catch (IOException | EncodeException ex1) {
                Logger.getLogger(PontoWebSocket.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    /**
     * Pauleia tudo, força o encerramento!
     *
     * @deprecated
     */
    @Deprecated
    private void finalizar() {
        desconectarComunicadores();
        System.exit(0);
    }

    private void desconectarComunicadores() {
        for (Comunicador comunicador : comunicadores.values()) {
            comunicador.getLocalMaquina().setAtivo(false);
        }
        comunicadores.clear();
    }
    
    private void desconectarPingadores() {
        for (Pingador pingador : pingadores.values()) {
            pingador.getMaquina().setAtiva(false);
        }
        pingadores.clear();
    }
}

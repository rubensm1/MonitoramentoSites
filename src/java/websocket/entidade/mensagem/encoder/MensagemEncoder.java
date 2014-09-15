package websocket.entidade.mensagem.encoder;

import com.google.gson.Gson;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import websocket.entidade.mensagem.Mensagem;

/**
 *
 * @author rubensmarcon
 */
public class MensagemEncoder implements Encoder.Text<Mensagem> {

    @Override
    public String encode(Mensagem mensagem) throws EncodeException {
        Gson gson = new Gson();
        //System.out.println(mensagem.getErro());
        MensagemJSON mensagemJSON = new MensagemJSON (
                mensagem.getTipo(), 
                mensagem.getDados().getClass().getSimpleName(),
                gson.toJson(mensagem.getDados()),
                mensagem.getErro());
        //System.out.println("encoder: mensagemJSON = " + mensagemJSON);
        return gson.toJson(mensagemJSON);
    }

    @Override
    public void init(EndpointConfig config) {
        System.out.println("init");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}

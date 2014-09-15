package websocket.entidade.mensagem.encoder;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import websocket.entidade.ClassesRegistradas;
import websocket.entidade.mensagem.Mensagem;

/**
 *
 * @author rubensmarcon
 */
public class MensagemDecoder implements Decoder.Text<Mensagem> {

    @Override
    public Mensagem decode(String arg0) throws DecodeException {
        Gson gson = new Gson();
        try {
            System.out.println("decode: " + arg0);
            return gson.fromJson(arg0, MensagemJSON.class).geraMensagem();
            //gson.fromJson(arg0, Mensagem.class);
        } catch (JsonSyntaxException ex) {
            Logger.getLogger(MensagemDecoder.class.getName()).log(Level.SEVERE, null, ex);
            //throw new ExceptionPersonalizada(ex);
        }
        return new Mensagem(null, null, null);
    }

    @Override
    public boolean willDecode(String arg0) {
        try {
            System.out.println("will dec...");
            Gson gson = new Gson();
            gson.fromJson(arg0, MensagemJSON.class).geraMensagem();
            return true;
        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
            return false;
        }
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

package websocket.entidade.mensagem.encoder;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import websocket.entidade.ClassesRegistradas;
import websocket.entidade.mensagem.Mensagem;

/**
 *
 * @author rubensmarcon
 */
class MensagemJSON {

    private final String tipo;
    private final String classe;
    private final String dados;
    private final String erro;

    public MensagemJSON(String tipo, String classe, String dados, String erro) {
        this.tipo = tipo;
        this.classe = classe;
        this.dados = dados;
        this.erro = erro;
    }

    public Mensagem geraMensagem() throws JsonSyntaxException {
        Gson gson = new Gson();
        Class classeOfT = ClassesRegistradas.getClasseRegistrada(this.classe);
        Object objeto = gson.fromJson(this.dados, classeOfT);
        return new Mensagem(this.tipo, objeto, this.erro);
    }

    public String getTipo() {
        return tipo;
    }

    public String getClasse() {
        return classe;
    }
    
    public String getErro() {
        return erro;
    }

    public String getDados() {
        return dados;
    }

    @Override
    public String toString() {
        return "MensagemJSON{" + "TIPO=" + tipo + ", CLASSE=" + classe + ", DADOS=" + dados + ", ERRO=" + erro + '}';
    }
}

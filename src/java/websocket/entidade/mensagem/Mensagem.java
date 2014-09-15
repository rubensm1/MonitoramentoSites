package websocket.entidade.mensagem;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 *
 * @author rubensmarcon
 */
public class Mensagem {
       
    protected String tipo;
    //private String classe;
    protected Object dados;
    protected String erro;

    public Mensagem(String tipo, Object dados, String erro) {
        this.tipo = tipo;
        this.dados = dados;
        this.erro = erro;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Object getDados() {
        return dados;
    }

    public void setDados(Object dados) {
        this.dados = dados;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    @Override
    public String toString() {
        return "Mensagem{" + "tipo=" + tipo + ", dados=" + dados + ", erro=" + erro + '}';
    }

    
}

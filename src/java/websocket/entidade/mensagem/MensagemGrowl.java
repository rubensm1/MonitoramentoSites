package websocket.entidade.mensagem;

import com.google.gson.Gson;
import javax.faces.application.FacesMessage;
import javax.swing.text.StyleConstants;

/**
 *
 * @author rubensmarcon
 */
public class MensagemGrowl {
    
    private enum IconeGrowl {
        
        warn("warn"), info("info"), error("error"), fatal("fatal");
        
        private String tipo;

        IconeGrowl(String tipo) {
            this.tipo = tipo;
        }

        public String getTipo() {
            return tipo;
        }
        
        public static IconeGrowl criaIconeGrow(FacesMessage.Severity icone) {
            if (icone.getOrdinal() == FacesMessage.SEVERITY_WARN.getOrdinal())
                return warn;
            if (icone.getOrdinal() == FacesMessage.SEVERITY_ERROR.getOrdinal())
                return error;
            if (icone.getOrdinal() == FacesMessage.SEVERITY_FATAL.getOrdinal())
                return fatal;
            return info;
        }

        @Override
        public String toString() {
            return tipo;
        }
        
    }
    
    private String titulo;
    private String descricao;
    private IconeGrowl icone;

    public MensagemGrowl(String titulo, String descricao, String icone) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.icone = IconeGrowl.valueOf(icone.toLowerCase());
    }
    
    public String toJsonString() {
        return "{\"titulo\":\""+this.titulo+"\",\"descricao\":\""+this.descricao+"\",\"icone\":\""+this.icone.getTipo()+"\"}";
    }
    
    public static String mensagemErro(Exception ex) {
        MensagemGrowl mensagemGrowl = new MensagemGrowl(
                ex.getClass().getSimpleName(), 
                ex.getMessage(), 
                "error");
        System.out.println(mensagemGrowl.toJsonString());
        return mensagemGrowl.toJsonString();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIcone() {
        return icone.toString();
    }

    public void setIcone(String icone) {
        this.icone = IconeGrowl.valueOf(icone.toUpperCase());
    }
    
    public void setIcone(FacesMessage.Severity icone) {
        this.icone = IconeGrowl.criaIconeGrow(icone);
    }
}

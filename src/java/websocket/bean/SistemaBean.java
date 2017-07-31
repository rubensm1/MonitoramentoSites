package websocket.bean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import websocket.entidade.Sistema;

/**
 *
 * @author rubensmarcon
 */
@ManagedBean
@ViewScoped
public class SistemaBean extends AbstractBean implements Serializable {

    private int id;
    private String nome;
    private String link;
        
    private boolean conectadoWebSocket = false;
    
    private Sistema sistemaTemp;

    public SistemaBean() {
        this.objetoPersistente = new Sistema(0);
    }
    
    @Override
    public void limpar(){
        this.objetoPersistente = new Sistema(0);
    }
    
    public void selecionar(Sistema objetoPersistente) {
        this.setSistema(objetoPersistente);
    }
    
    public void selll (Sistema ob) {
        System.out.println(ob.getNome());
    }

    /*@Override
    public List<? extends Persistente> getLista() {
        try {
            return ConexaoEntityManager.listar(LocalMaquina.class);
        } catch (ExceptionPersonalizada e) {
            novaMensagem(e.geraFacesMessage());
            Logger.getLogger(LocalBean.class.getName()).log(Level.SEVERE, null, e);
        }
        return new ArrayList<>();
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isConectadoWebSocket() {
        return conectadoWebSocket;
    }

    public void setConectadoWebSocket(boolean conectadoWebSocket) {
        this.conectadoWebSocket = conectadoWebSocket;
    }

    public void setSistema(Sistema objetoPersistente) {
        this.objetoPersistente = objetoPersistente;
        /*this.id = local.getId();
        this.nome = local.getNome();
        this.link = local.getUrl();*/
    }
    
    public Sistema getSistemaTemp() {
        return sistemaTemp;
    }

    public void setSistemaTemp(Object sistemaTemp) {
        System.out.println(this.sistemaTemp);
        //this.localTemp = localTemp;
    }
    
    public void mens() {
        novaMensagem("aeeeee", "descricao", FacesMessage.SEVERITY_FATAL);
    }

}

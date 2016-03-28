package websocket.bean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import websocket.entidade.LocalMaquina;

/**
 *
 * @author rubensmarcon
 */
@ManagedBean
@ViewScoped
public class LocalBean extends AbstractBean implements Serializable {

    private int id;
    private String nome;
    private String link;
        
    private boolean conectadoWebSocket = false;
    
    private LocalMaquina localTemp;

    public LocalBean() {
        this.objetoPersistente = new LocalMaquina(0);
    }
    
    @Override
    public void limpar(){
        this.objetoPersistente = new LocalMaquina(0);
    }
    
    public void selecionar(LocalMaquina objetoPersistente) {
        this.setLocalMaquina(objetoPersistente);
    }
    
    public void selll (LocalMaquina ob) {
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

    public void setLocalMaquina(LocalMaquina objetoPersistente) {
        this.objetoPersistente = objetoPersistente;
        /*this.id = local.getId();
        this.nome = local.getNome();
        this.link = local.getUrl();*/
    }
    
    public LocalMaquina getLocalTemp() {
        return localTemp;
    }

    public void setLocalTemp(Object localTemp) {
        System.out.println(this.localTemp);
        //this.localTemp = localTemp;
    }
    
    public void mens() {
        novaMensagem("aeeeee", "descricao", FacesMessage.SEVERITY_FATAL);
    }

}

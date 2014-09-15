package websocket.bean;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import util.ExceptionPersonalizada;
import websocket.controle.ConexaoEntityManager;
import websocket.entidade.LocalMaquina;

/**
 *
 * @author rubensmarcon
 */
@ManagedBean
@ViewScoped
public class LocalBean implements Serializable {

    private int id;
    private String nome;
    private String link;
    
    LocalMaquina localMaquina = new LocalMaquina(0);
    
    private boolean conectadoWebSocket = false;
    
    private LocalMaquina localTemp;
    
    public void inserir() {
        try {
            if (this.localMaquina.getId() == 0 || this.localMaquina.getId() == null){
                ConexaoEntityManager.inserir(this.localMaquina);
                novaMensagem("Sucesso!", "Incerção bem Sucedida!", null);
            }
            else
                novaMensagem("Falha!", "Tentativa de Inserção em um ID já existente. Limpe os campos para inserir, ou então utilize 'Alterar'", FacesMessage.SEVERITY_WARN);
            //RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("Sucesso!",  "Incerção bem Sucedida!"));
            
        } catch (ExceptionPersonalizada ex) {
            novaMensagem(ex.geraFacesMessage());
            Logger.getLogger(LocalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        //novaMensagem("Teste", "Easasaas", FacesMessage.SEVERITY_WARN);
    }
    
    public void alterar() {
        try {
            if (localMaquina.getId() > 0){
                ConexaoEntityManager.alterar(localMaquina);
                novaMensagem("Sucesso!", "Alteração bem Sucedida!", null);
            }
            else 
                novaMensagem("Falha!", "Não há itens selecionados, selecione um item para alterar, ou então use 'Inserir'.", FacesMessage.SEVERITY_WARN);
        } catch (ExceptionPersonalizada ex) {
            novaMensagem(ex.geraFacesMessage());
            Logger.getLogger(LocalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void excluir() {
        try {
            if (localMaquina.getId() > 0) {
                ConexaoEntityManager.deletar(localMaquina);
                novaMensagem("Sucesso!", "Exclusão bem Sucedida!", null);
            }
            else {
                novaMensagem("Falha!", "Não há itens selecionados, selecione um item para excluir", FacesMessage.SEVERITY_WARN);
            }
        } catch (ExceptionPersonalizada ex) {
            novaMensagem(ex.geraFacesMessage());
            Logger.getLogger(LocalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void limpar(){
        this.localMaquina = new LocalMaquina(0);
    }
    
    public void selecionar(LocalMaquina localMaquina) {
        this.setLocalMaquina(localMaquina);
    }

    public List<LocalMaquina> getLocais() {
        try {
            return LocalMaquina.listar();
        } catch (ExceptionPersonalizada e) {
            novaMensagem(e.geraFacesMessage());
            Logger.getLogger(LocalBean.class.getName()).log(Level.SEVERE, null, e);
        }
        return new ArrayList<>();
    }

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

    public LocalMaquina getLocalMaquina() {
        return localMaquina;
        /*try {
            return new LocalMaquina(this.id, this.nome, this.link);
        } catch (Exception e) {
            return new LocalMaquina(this.id, this.nome);
        }*/
    }

    public void setLocalMaquina(LocalMaquina localMaquina) {
        this.localMaquina = localMaquina;
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
    
    public void novaMensagem(String titulo, String detalhes, FacesMessage.Severity icone) {
        if (icone == null)
            icone = FacesMessage.SEVERITY_INFO;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(icone, titulo, detalhes));
    }
    public void novaMensagem(FacesMessage facesMessage) {
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

}

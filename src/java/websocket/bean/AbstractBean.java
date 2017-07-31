package websocket.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import util.ExceptionPersonalizada;
import websocket.controle.ConexaoEntityManager;
import websocket.entidade.Persistente;

/**
 *
 * @author rubensmarcon
 */
public abstract class AbstractBean {
    
    protected Persistente objetoPersistente;
    
    public void inserir() {
        try {
            if (this.objetoPersistente.getId() == 0 || this.objetoPersistente.getId() == null){
                ConexaoEntityManager.inserir(this.objetoPersistente);
                novaMensagem("Sucesso!", "Incerção bem Sucedida!", null);
            }
            else
                novaMensagem("Falha!", "Tentativa de Inserção em um ID já existente. Limpe os campos para inserir, ou então utilize 'Alterar'", FacesMessage.SEVERITY_WARN);
            //RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("Sucesso!",  "Incerção bem Sucedida!"));
            
        } catch (ExceptionPersonalizada ex) {
            novaMensagem(ex.geraFacesMessage());
            Logger.getLogger(SistemaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        //novaMensagem("Teste", "Easasaas", FacesMessage.SEVERITY_WARN);
    }
    
    public void alterar() {
        try {
            if (objetoPersistente.getId() > 0){
                ConexaoEntityManager.alterar(objetoPersistente);
                novaMensagem("Sucesso!", "Alteração bem Sucedida!", null);
            }
            else 
                novaMensagem("Falha!", "Não há itens selecionados, selecione um item para alterar, ou então use 'Inserir'.", FacesMessage.SEVERITY_WARN);
        } catch (ExceptionPersonalizada ex) {
            novaMensagem(ex.geraFacesMessage());
            Logger.getLogger(SistemaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void excluir() {
        try {
            if (objetoPersistente.getId() > 0) {
                ConexaoEntityManager.deletar(objetoPersistente);
                novaMensagem("Sucesso!", "Exclusão bem Sucedida!", null);
            }
            else {
                novaMensagem("Falha!", "Não há itens selecionados, selecione um item para excluir", FacesMessage.SEVERITY_WARN);
            }
        } catch (ExceptionPersonalizada ex) {
            novaMensagem(ex.geraFacesMessage());
            Logger.getLogger(SistemaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public abstract void limpar();
    
   // public abstract List<? extends Persistente> getLista();

    public List<? extends Persistente> getLista() {
        try {
            return ConexaoEntityManager.listar(this.objetoPersistente.getClass());
        } catch (ExceptionPersonalizada e) {
            novaMensagem(e.geraFacesMessage());
            Logger.getLogger(SistemaBean.class.getName()).log(Level.SEVERE, null, e);
        }
        return new ArrayList<>();
    }
    
    public Persistente getObjetoPersistente() {
        return objetoPersistente;
    }

    public void setObjetoPersistente(Persistente objetoPersistente) {
        this.objetoPersistente = objetoPersistente;
    }
    
    public boolean isSelecionado() {
        return this.objetoPersistente == null || this.objetoPersistente.getId() == null ? false : this.objetoPersistente.getId() > 0;
    }
    
    public void novaMensagem(String titulo, String detalhes, FacesMessage.Severity icone) {
        if (icone == null) {
            icone = FacesMessage.SEVERITY_INFO;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(icone, titulo, detalhes));
    }

    public void novaMensagem(FacesMessage facesMessage) {
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
    
    public String getLinkPaginaInicial() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml";
    }

}

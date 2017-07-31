package websocket.bean;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import util.ExceptionPersonalizada;
import websocket.controle.ConexaoEntityManager;
import websocket.controle.Navegador;
import websocket.entidade.Maquina;

/**
 *
 * @author rubensmarcon
 */
@ManagedBean
@ViewScoped
public class MaquinaBean extends AbstractBean implements Serializable {

    public MaquinaBean() {
        objetoPersistente = new Maquina(0);
    }

    @Override
    public void inserir() {
        try {
            if (this.objetoPersistente.getId() == 0 || this.objetoPersistente.getId() == null) {
                InetAddress ia = InetAddress.getByName(((Maquina) this.objetoPersistente).getEndereco());
                //((Maquina)this.objetoPersistente).setNet(ia);
                ((Maquina) this.objetoPersistente).setIp(ia.getHostAddress());
                ConexaoEntityManager.inserir(this.objetoPersistente);
                novaMensagem("Sucesso!", "Incerção bem Sucedida!", null);
            } else {
                novaMensagem("Falha!", "Tentativa de Inserção em um ID já existente. Limpe os campos para inserir, ou então utilize 'Alterar'", FacesMessage.SEVERITY_WARN);
            }
            //RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("Sucesso!",  "Incerção bem Sucedida!"));

        } catch (ExceptionPersonalizada ex) {
            novaMensagem(ex.geraFacesMessage());
            Logger.getLogger(SistemaBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            novaMensagem(new ExceptionPersonalizada(ex).geraFacesMessage());
            Logger.getLogger(SistemaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void alterar() {
        try {
            if (objetoPersistente.getId() > 0) {
                InetAddress ia = InetAddress.getByName(((Maquina) this.objetoPersistente).getEndereco());
                //((Maquina)this.objetoPersistente).setNet(ia);
                ((Maquina) this.objetoPersistente).setIp(ia.getHostAddress());
                ConexaoEntityManager.alterar(objetoPersistente);
                novaMensagem("Sucesso!", "Alteração bem Sucedida!", null);
            } else {
                novaMensagem("Falha!", "Não há itens selecionados, selecione um item para alterar, ou então use 'Inserir'.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ExceptionPersonalizada ex) {
            novaMensagem(ex.geraFacesMessage());
            Logger.getLogger(SistemaBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            novaMensagem(new ExceptionPersonalizada(ex).geraFacesMessage());
            Logger.getLogger(SistemaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void limpar() {
        this.objetoPersistente = new Maquina(0);
    }

    public void selecionar(Maquina objetoPersistente) {
        this.setObjetoPersistente(objetoPersistente);
    }

    /*@Override
     public List<? extends Persistente> getLista() {
     try {
     return ConexaoEntityManager.listar((Class<? extends Persistente>)this.getClass());
     } catch (ExceptionPersonalizada e) {
     novaMensagem(e.geraFacesMessage());
     Logger.getLogger(LocalBean.class.getName()).log(Level.SEVERE, null, e);
     }
     return new ArrayList<>();
     }*/
}

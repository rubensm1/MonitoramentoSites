package websocket.bean;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import util.ExceptionPersonalizada;
import util.SessionContext;
import websocket.controle.ConexaoEntityManager;
import websocket.entidade.Perfil;
import websocket.entidade.Usuario;

/**
 *
 * @author rubensmarcon
 */
@ManagedBean
@SessionScoped
public class UsuarioBean extends AbstractBean implements Serializable {

    public UsuarioBean() {

    }

    @Override
    public void inserir() {
        ((Usuario) this.objetoPersistente).setSenha(this.geraHashSenha(((Usuario) this.objetoPersistente).getSenha()));
        super.inserir();
    }

    @Override
    public void alterar() {
        ((Usuario) this.objetoPersistente).setSenha(this.geraHashSenha(((Usuario) this.objetoPersistente).getSenha()));
        super.alterar();
    }

    @PostConstruct
    @Override
    public void limpar() {
        this.objetoPersistente = new Usuario(0, "", "", "", Perfil.INATIVO);
    }

    public void selecionar(Usuario objetoPersistente) {
        this.setObjetoPersistente(objetoPersistente);
    }

    public List<Perfil> allPerfis() {
        return Arrays.asList(Perfil.values());
    }

    public Usuario validaLogin() {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("login", ((Usuario)this.objetoPersistente).getLogin());
            parametros.put("senha", this.geraHashSenha(((Usuario) this.objetoPersistente).getSenha()));
            List<Usuario> usuarios = (List<Usuario>) ConexaoEntityManager.listarNamedQuery(this.objetoPersistente.getClass(), "findByLogin", parametros);
            return usuarios.isEmpty() ? new Usuario(0) : usuarios.get(0);
        } catch (ExceptionPersonalizada e) {
            novaMensagem(e.geraFacesMessage());
            Logger.getLogger(LocalBean.class.getName()).log(Level.SEVERE, null, e);
        }
        return new Usuario(0);
    }
    
    public String geraHashSenha(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(senha.getBytes());
            return new String(md.digest());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return senha;
    }

    public String cadastrar() {
        if(objetoPersistente.getId() == null || objetoPersistente.getId() == 0) 
            this.inserir();
        else 
            this.alterar();
        return "";
    }
    
    public Usuario getUser() {
        return (Usuario) SessionContext.getInstance().getUsuarioLogado();
    }
    
    public String getStatusLoginUser () {
        return getUser() == null ? "Aguardando Login..." : "Logado Como: " + getUser().getNome();
    }
    
    public String getTextoBotaoLogin() {
        return getUser() == null ? "Login" : "Logoff";
    }
    
    public String getLinkLoginLogoff() {
        if (getUser() == null) {
            return "/login.xhtml";
        }
        else {
            doLogout();
            return "";
        }
    }

    public void doLogin() {
        Usuario user = validaLogin();
        if (user.getId() > 0) {
            if (user.getPerfil() != null && user.getPerfil() == Perfil.INATIVO) {
                super.novaMensagem("Usuário Inativo", "", FacesMessage.SEVERITY_WARN);
                return;
            }
            SessionContext.getInstance().setAttribute("usuarioLogado", user);
            redirecionar((String) SessionContext.getInstance().getAttribute("link"));
        }
        else {
            super.novaMensagem("Usuário ou senha inválidos", "", FacesMessage.SEVERITY_WARN);
        }
    }

    public void doLogout() {
        SessionContext.getInstance().encerrarSessao();
        redirecionar(getLinkPaginaInicial());
    }

    public void redirecionar(String url) {System.out.println(url);
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.getSession(true);
            ec.redirect(url == null ? getLinkPaginaInicial() : url);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private Boolean isPermitido(String tipo) {
        Usuario usuario = getUser();
        if (usuario == null)
            return false;
        switch (tipo) {
            case "administrador":
                return usuario.getPerfil() == Perfil.ADMINISTRADOR;
            case "comum":
                return usuario.getPerfil() == Perfil.ADMINISTRADOR || usuario.getPerfil() == Perfil.COMUM;
            default:
                return false;
        }
    }
    public Boolean isPermitidoAdministrador() {
        return isPermitido("administrador");
    }
    public Boolean isPermitidoComum() {
        return isPermitido("comum");
    }
}

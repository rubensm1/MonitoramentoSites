/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package websocket.entidade;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import util.ExceptionPersonalizada;
import websocket.controle.ConexaoEntityManager;

/**
 *
 * @author rubensmarcon
 */
@Entity
@Table(name = "localmaquina", schema = "monitorador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LocalMaquina.findAll", query = "SELECT lm FROM LocalMaquina lm ORDER BY lm.id"),
    @NamedQuery(name = "LocalMaquina.findById", query = "SELECT l FROM LocalMaquina l WHERE l.id = :id"),
    @NamedQuery(name = "LocalMaquina.findByNome", query = "SELECT l FROM LocalMaquina l WHERE l.nome = :nome"),
    @NamedQuery(name = "LocalMaquina.findByUrl", query = "SELECT l FROM LocalMaquina l WHERE l.url = :url")})
public class LocalMaquina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "nome", nullable = false, length = 32)
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "url", nullable = false, length = 128)
    private String url;
    
    @Transient
    private Boolean ativa = false;
    
    public LocalMaquina() {
    }

    public LocalMaquina(Integer id) {
        this.id = id;
    }

    public LocalMaquina(Integer id, String nome, String link) throws ExceptionPersonalizada {
        this.id = id;
        this.nome = nome;
        try {
            this.url = new URL(link).toExternalForm();
        } catch (MalformedURLException ex) {
            throw new ExceptionPersonalizada(ex);
        }
    }
    
    public LocalMaquina(Integer id, String nome, String link, Boolean ativa) throws ExceptionPersonalizada {
        this.id = id;
        this.nome = nome;
        try {
            this.url = new URL(link).toExternalForm();
        } catch (MalformedURLException ex) {
            throw new ExceptionPersonalizada(ex);
        }
        this.ativa = ativa;
    }

    public LocalMaquina(Integer id, String nome, URL url) {
        this.id = id;
        this.nome = nome;
        this.url = url.toExternalForm();
    }

    public LocalMaquina(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public LocalMaquina(String nome, String link) throws ExceptionPersonalizada {
        this.nome = nome;
        try {
            this.url = new URL(link).toExternalForm();
        } catch (MalformedURLException ex) {
            throw new ExceptionPersonalizada(ex);
        }
    }

    public LocalMaquina(String nome, URL url) {
        this.nome = nome;
        this.url = url.toExternalForm();
    }

    public static List<LocalMaquina> listar() throws ExceptionPersonalizada {
        EntityManager entityManager = ConexaoEntityManager.getEntityManager();
        List<LocalMaquina> localMaquinas;
        try {
            entityManager.getTransaction().begin();
            localMaquinas = entityManager.createNamedQuery("LocalMaquina.findAll").getResultList();
            entityManager.getTransaction().commit();
            //entityManager.close();
            return localMaquinas;
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            //entityManager.close();
            throw new ExceptionPersonalizada(e);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }
    
    public boolean isAtiva() {
        return this.ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
    public void setUrl(String url) {
        try {
            this.url = new URL(url).toExternalForm();
        } catch (MalformedURLException ex) {
            Logger.getLogger(LocalMaquina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setUrl(URL url) {
        this.url = url.toExternalForm();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LocalMaquina)) {
            return false;
        }
        LocalMaquina other = (LocalMaquina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LocalMaquina{" + "id=" + id + ", nome=" + nome + ", url=" + url + ", ativa=" + ativa + '}';
        //return "websocket.entidade.LocalMaquina[ id=" + id + " ]";
    }
    
}

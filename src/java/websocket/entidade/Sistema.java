/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package websocket.entidade;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 *
 * @author rubensmarcon
 */
@Entity
@Table(name = "localmaquina", schema = "monitorador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sistema.findAll", query = "SELECT lm FROM Sistema lm ORDER BY lm.id"),
    @NamedQuery(name = "Sistema.findById", query = "SELECT l FROM Sistema l WHERE l.id = :id"),
    @NamedQuery(name = "Sistema.findByNome", query = "SELECT l FROM Sistema l WHERE l.nome = :nome"),
    @NamedQuery(name = "Sistema.findByUrl", query = "SELECT l FROM Sistema l WHERE l.url = :url")})
public class Sistema implements Persistente{
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
    private Boolean ativo = false;
    
    public Sistema() {
    }

    public Sistema(Integer id) {
        this.id = id;
    }

    public Sistema(Integer id, String nome, String link) throws ExceptionPersonalizada {
        this.id = id;
        this.nome = nome;
        try {
            this.url = new URL(link).toExternalForm();
        } catch (MalformedURLException ex) {
            throw new ExceptionPersonalizada(ex);
        }
    }
    
    public Sistema(Integer id, String nome, String link, Boolean ativa) throws ExceptionPersonalizada {
        this.id = id;
        this.nome = nome;
        try {
            this.url = new URL(link).toExternalForm();
        } catch (MalformedURLException ex) {
            throw new ExceptionPersonalizada(ex);
        }
        this.ativo = ativa;
    }

    public Sistema(Integer id, String nome, URL url) {
        this.id = id;
        this.nome = nome;
        this.url = url.toExternalForm();
    }

    public Sistema(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Sistema(String nome, String link) throws ExceptionPersonalizada {
        this.nome = nome;
        try {
            this.url = new URL(link).toExternalForm();
        } catch (MalformedURLException ex) {
            throw new ExceptionPersonalizada(ex);
        }
    }

    public Sistema(String nome, URL url) {
        this.nome = nome;
        this.url = url.toExternalForm();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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
    
    public boolean isAtivo() {
        return this.ativo;
    }

    public void setAtivo(boolean ativa) {
        this.ativo = ativa;
    }
    public void setUrl(String url) {
        try {
            this.url = new URL(url).toExternalForm();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
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
        if (!(object instanceof Sistema)) {
            return false;
        }
        Sistema other = (Sistema) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sistema{" + "id=" + id + ", nome=" + nome + ", url=" + url + ", ativa=" + ativo + '}';
        //return "websocket.entidade.Sistema[ id=" + id + " ]";
    }
    
}

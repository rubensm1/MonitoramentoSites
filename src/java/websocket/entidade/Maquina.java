/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket.entidade;

import java.net.InetAddress;
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

/**
 *
 * @author rubensmarcon
 */
@Entity
@Table(name = "maquina", schema = "monitorador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maquina.findAll", query = "SELECT m FROM Maquina m"),
    @NamedQuery(name = "Maquina.findById", query = "SELECT m FROM Maquina m WHERE m.id = :id"),
    @NamedQuery(name = "Maquina.findByNome", query = "SELECT m FROM Maquina m WHERE m.nome = :nome"),
    @NamedQuery(name = "Maquina.findByEndereco", query = "SELECT m FROM Maquina m WHERE m.endereco = :endereco"),
    @NamedQuery(name = "Maquina.findByIp", query = "SELECT m FROM Maquina m WHERE m.ip = :ip")})
public class Maquina implements Persistente {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "nome")
    private String nome;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "endereco")
    private String endereco;
    
    @Size(max = 16)
    @Column(name = "ip")
    private String ip;
    
    @Transient
    private InetAddress net;
    
    @Transient
    private Boolean ativa = false;
    
    public Maquina() {
    }

    public Maquina(Integer id) {
        this.id = id;
    }

    public Maquina(Integer id, String nome, String endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
    }

    public Maquina(Integer id, String nome, String endereco, String ip, Boolean ativa) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.ip = ip;
        this.ativa = ativa;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
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
        if (!(object instanceof Maquina)) {
            return false;
        }
        Maquina other = (Maquina) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Maquina{" + "id=" + id + '}';
    }

    public InetAddress getNet() {
        return net;
    }

    public void setNet(InetAddress net) {
        this.net = net;
    }
}
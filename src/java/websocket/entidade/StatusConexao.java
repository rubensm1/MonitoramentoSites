package websocket.entidade;

/**
 *
 * @author rubensmarcon
 */
public class StatusConexao {
    
    private Integer id;
    private long delay;
    private String descricao;    

    public StatusConexao(){}
    
    public StatusConexao(Integer id, long delay, String descricao) {
        this.id = id;
        this.delay = delay;
        this.descricao = descricao;
    }

    public StatusConexao(long delay, String descricao) {
        this.delay = delay;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        //return "StatusConexao{"+ id + '}';
        return "StatusConexao{" + "id=" + id + ", delay=" + delay + ", descricao=" + descricao + '}';
    }

    
    
    
    
}

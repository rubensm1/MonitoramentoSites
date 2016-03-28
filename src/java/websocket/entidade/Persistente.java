package websocket.entidade;

import java.io.Serializable;

/**
 *
 * @author rubensmarcon
 */
public interface Persistente extends Serializable {
    
    public Integer getId();

    public void setId(Integer id);
    
}

package util;

import javax.faces.application.FacesMessage;

/**
 *
 * @author rubensmarcon
 */
public class ExceptionPersonalizada extends Exception{
    
    private final String titulo;
    private final String detalhes;

    public ExceptionPersonalizada(Exception exception) {
        this.titulo = exception.getClass().getName();
        this.detalhes = exception.getLocalizedMessage();
    }
    
    public FacesMessage geraFacesMessage() {
        return new FacesMessage(FacesMessage.SEVERITY_ERROR, this.titulo, this.detalhes);
    }
    
}

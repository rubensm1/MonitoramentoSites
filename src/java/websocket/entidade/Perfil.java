/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket.entidade;

/**
 *
 * @author rubensmarcon
 */
public enum Perfil {
    
    ADMINISTRADOR("ADMINISTRADOR"),
    COMUM("COMUM"),
    INATIVO("INATIVO");

    private final String perfil;

    private Perfil(String perfil) {
        this.perfil = perfil;
    }

    public String getPerfil() {
        return this.perfil;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket.filter;

import java.util.ArrayList;
import java.util.List;
import websocket.entidade.Perfil;

/**
 *
 * @author rubensmarcon
 */
public class AdministradorFilter extends AbstractFilter{

    @Override
    public List<Perfil> getPerfis() {
        List<Perfil> perfis = new ArrayList<>();
        perfis.add(Perfil.ADMINISTRADOR);
        return perfis;
    }
}

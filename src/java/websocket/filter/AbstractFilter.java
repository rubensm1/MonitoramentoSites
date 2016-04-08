package websocket.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import javax.servlet.http.HttpSession;
import websocket.entidade.Perfil;
import websocket.entidade.Usuario;

/**
 *
 * @author rubensmarcon
 */
public abstract class AbstractFilter implements Filter {
    
    @Override
    public void destroy() {
        // TODO Auto-generated method stub 
    }

    public abstract List<Perfil> getPerfis();
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Usuario user = null;
        HttpSession sess = ((HttpServletRequest) request).getSession(false);
        
        //String link = ((HttpServletRequest) request).getRequestURI().replace(((HttpServletRequest) request).getContextPath(), "");
        String link = ((HttpServletRequest) request).getRequestURI();
       
        if (sess != null) {
            user = (Usuario) sess.getAttribute("usuarioLogado");
            sess.setAttribute("link", link == null? "" : link);
        }
        if (user == null) {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");
        } else {
            for (Perfil perfil : getPerfis()) {
                if (user.getPerfil() == perfil ) {
                    chain.doFilter(request, response);
                    return;
                }
            }
            ((HttpServletResponse) response).sendError(SC_UNAUTHORIZED);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub 
    }
}

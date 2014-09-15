package websocket.controle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author rubensmarcon
 */
public class ConexaoBD {

    private final String URL = "jdbc:postgresql://192.168.56.102:5432/HelloWorld";
    private final String USER = "postgres";
    private final String PASS = "post";

    private Connection conexao;
    private boolean conectado = false;

    public void conectar() throws SQLException {
        conexao = DriverManager.getConnection(URL, USER, PASS);
        conectado = true;
    }
    
    public Statement getStatement() throws SQLException {
        if (conectado) 
            return conexao.createStatement();
        return null;
    }

    public void finalizar() throws SQLException {
        conexao.close();
    }
}

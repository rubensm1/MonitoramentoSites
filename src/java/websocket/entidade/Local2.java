package websocket.entidade;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import websocket.controle.ConexaoBD;

/**
 *
 * @author rubensmarcon
 */
public class Local2 {

    private int id;
    private String nome;
    private URL url;

    public Local2(int id, String nome, URL url) {
        this.id = id;
        this.nome = nome;
        this.url = url;
    }

    public Local2(int id, String nome, String link) throws MalformedURLException {
        this.id = id;
        this.nome = nome;
        this.url = new URL(link);
    }

    public Local2(String nome, URL url) {
        this.nome = nome;
        this.url = url;
    }

    public Local2(String nome, String link) throws MalformedURLException {
        this.nome = nome;
        this.url = new URL(link);
    }

    public Local2(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int inserir() throws SQLException {
        if (this.url == null || this.nome == null) {
            return -1;
        }
        ConexaoBD conexaoBD = new ConexaoBD();
        conexaoBD.conectar();
        final String SQL;
        int result;
        Statement statement;
        if (this.id > 0) {
            SQL = "UPDATE monitorador.local SET nome='" + this.nome + "', url='" + this.url.toExternalForm() + "' WHERE id=" + this.id;
            statement = conexaoBD.getStatement();
            result = statement.executeUpdate(SQL);
        } else {
            SQL = "INSERT INTO monitorador.local (nome, url) VALUES ('" + this.nome + "','" + this.url.toExternalForm() + "')";
            statement = conexaoBD.getStatement();
            result = statement.executeUpdate(SQL);
            if (result > 0) {
                ResultSet ultimo = statement.executeQuery("SELECT MAX(id) as max FROM monitorador.local;");
                if (ultimo.first()) {
                    result = ultimo.getInt("max");
                } else {
                    result = 0;
                }
            } else {
                result = 0;
            }
        }
        statement.close();
        conexaoBD.finalizar();
        return result;
    }

    public int excluir() throws SQLException {
        ConexaoBD conexaoBD = new ConexaoBD();
        conexaoBD.conectar();
        final String SQL;
        if (this.id > 0) {
            SQL = "DELETE FROM monitorador.local WHERE id=" + this.id;
        } else {
            return -1;
        }
        Statement statement = conexaoBD.getStatement();
        int result = statement.executeUpdate(SQL);
        statement.close();
        conexaoBD.finalizar();
        return result;
    }

    public static Local2 consultar(int id) throws SQLException {
        ConexaoBD conexaoBD = new ConexaoBD();
        conexaoBD.conectar();
        final String SQL = "SELECT * FROM monitorador.local WHERE id='" + id + "'";
        Statement statement = conexaoBD.getStatement();
        ResultSet result = statement.executeQuery(SQL);

        if (result.first()) {
            Local2 local;
            try {
                URL url = new URL(result.getString("url"));
                return new Local2(result.getInt("id"), result.getString("nome"), url);
            } catch (MalformedURLException ex) {
                return new Local2(result.getInt("id"), result.getString("nome"));
            }
        }
        statement.close();
        result.close();
        conexaoBD.finalizar();
        return null;
    }

    public static List<Local2> consultarLista() throws SQLException {
        ConexaoBD conexaoBD = new ConexaoBD();
        conexaoBD.conectar();
        final String SQL = "SELECT * FROM monitorador.local";
        Statement statement = conexaoBD.getStatement();
        ResultSet result = statement.executeQuery(SQL);
        List<Local2> listaLocais = new ArrayList<>();

        while (result.next()) {
            Local2 local;
            try {
                URL url = new URL(result.getString("url"));
                local = new Local2(result.getInt("id"), result.getString("nome"), url);
            } catch (MalformedURLException ex) {
                local = new Local2(result.getInt("id"), result.getString("nome"));
            }
            listaLocais.add(local);
        }
        statement.close();
        result.close();
        conexaoBD.finalizar();
        return listaLocais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

}

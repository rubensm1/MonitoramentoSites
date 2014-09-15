package websocket.controle;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import websocket.entidade.StatusConexao;

/**
 *
 * @author rubensmarcon
 */
public class Navegador {

    private final String USER_AGENT = "Mozilla/5.0";
    private URL url;

    public Navegador(String link) throws MalformedURLException {
        this.url = new URL(link);
    }

    public StatusConexao ping(Integer idStatus) {
        StatusConexao statusConexao;
        long delay;
        try {
            delay = new Date().getTime();

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            //System.out.println(con.getResponseCode());
            //System.out.println(con.getHeaderFields());
            String resposta = con.getResponseMessage();
            //con.disconnect();

            delay = new Date().getTime() - delay;
            statusConexao = new StatusConexao(idStatus, delay, resposta != null ? resposta : "ERROR: "+ con.getResponseCode());
        } catch (IOException ex) {
            Logger.getLogger(Navegador.class.getName()).log(Level.SEVERE, null, ex);
            statusConexao = new StatusConexao(idStatus, -1, ex.getMessage());
        }
        return statusConexao;
    }

    public String sendGet() throws IOException {

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        //int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'GET' request to URL : " + url);
        //System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

    private void sendPost() throws Exception {

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }
}

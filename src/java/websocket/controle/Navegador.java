package websocket.controle;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import websocket.entidade.Maquina;
import websocket.entidade.StatusConexao;

/**
 *
 * @author rubensmarcon
 */
public class Navegador {

    private final String USER_AGENT = "Mozilla/5.0";
    private URL url;
    private Maquina maquina;

    public Navegador(String link) throws MalformedURLException {
        this.url = new URL(link);
    }
    
    public Navegador(Maquina maquina) {
        this.maquina = maquina;
    }

    public StatusConexao pingLink(Integer idStatus) {
        StatusConexao statusConexao;
        long delay;
        try {
            delay = new Date().getTime();
            HttpURLConnection con = connectPing();
	    //con.disconnect();
            if (con == null) {
                statusConexao = new StatusConexao(idStatus, delay, "Erro Desconhecido!") ;
            }
            else {
                int code = con.getResponseCode();
                String resposta = con.getResponseMessage();
                //if (code == 301 || code == 302) {
                    String redrString = con.getHeaderField("Location");
                    if (redrString != null) {
                        /*con = connectPing(new URL(redrString));
                        if (con != null) {
                            code = con.getResponseCode();
                            resposta = con.getResponseMessage();
                        }*/
                        code = 200;
                        resposta = "OK";
                    }
                //}
                delay = new Date().getTime() - delay;
                statusConexao = new StatusConexao(idStatus, delay, resposta != null ? resposta : "ERROR: " + code );
            }
            
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException ex) {
            Logger.getLogger(Navegador.class.getName()).log(Level.SEVERE, null, ex);
            statusConexao = new StatusConexao(idStatus, -1, ex.getMessage());
        }
        return statusConexao;
    }

    private HttpURLConnection connectPing() throws IOException, KeyManagementException, NoSuchAlgorithmException {
        return connectPing(this.url);
    }
    
    private HttpURLConnection connectPing(URL url) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        
        switch (url.getProtocol()) {
            case "http":
            {
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();
                return con;
            }
            case "https":
            {
                
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                
                TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String string) throws CertificateException {
                        }
                        
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String string) throws CertificateException {
                        }
                        
                    }
                };
                
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                
                con.setSSLSocketFactory(sc.getSocketFactory());
                con.setHostnameVerifier(new javax.net.ssl.HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
                        return true;
                    }
                });
                con.connect();
                return con;
            }
        }
        return null;
    }
    
    public boolean pingIp() {
        if (this.maquina == null && this.maquina.getNet() != null)
            return false;
        try {
            return this.maquina.getNet().isReachable(5000);
        } catch (IOException ex) {
            Logger.getLogger(Navegador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
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

    public void buscarIP() throws IOException {

        final HttpURLConnection urlConnection = getHttpUrlConnectionAreaCliente();

        urlConnection.setUseCaches(false);
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);

        InetAddress ia = InetAddress.getByName(url.getHost());
        /*final String resposta = readStringResponse(urlConnection);

        if (!resposta.isEmpty()) {
            //final ObjectMapper mapper = getObjectMapper();
            //return mapper.readValue(resposta, new TypeReference<List<ProdutoVersaoVO>>() {
            //});
            
            System.out.println(resposta);
        } else {
            System.out.println("Sem Resposta!");
        }*/
    }

    private static String readStringResponse(HttpURLConnection urlConnection) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        final StringBuilder str = new StringBuilder("");
        String line = "";

        while ((line = in.readLine()) != null) {
            str.append(line);
        }

        return str.toString();
    }

    private HttpURLConnection getHttpUrlConnectionAreaCliente() throws IOException {
        return (HttpURLConnection) url.openConnection();
    }
}

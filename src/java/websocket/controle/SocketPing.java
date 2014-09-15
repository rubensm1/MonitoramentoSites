
/***** NÃO UTILIZADA *****/

package websocket.controle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rubensmarcon
 */
public class SocketPing {
    
    private Socket socket;// = new Socket("biblioteca.ufgd.edu.br", 80);
    private DataInputStream in;// = new DataInputStream(socket.getInputStream());
    private DataOutputStream out;// = new DataOutputStream(socket.getOutputStream());

    public SocketPing(String url, int porta) throws IOException {
        this.socket = new Socket(url, porta);
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
    }

    public String requisicao() throws IOException {
        out.writeChars("a");
        return lerInputStreamCompleto(in);
    }
    
    public boolean matar() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketPing.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static String lerInputStreamCompleto(InputStream in) {
        StringBuilder builder = new StringBuilder();
        byte[] b = new byte[128];
        int lidos;
        try {
            while (true) {
                lidos = in.read(b);
                if (lidos <= 0) {
                    break;
                }
                String buffer = new String(b, 0, lidos);
                builder.append(buffer);
            }
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketPing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return builder.toString();
    }
}

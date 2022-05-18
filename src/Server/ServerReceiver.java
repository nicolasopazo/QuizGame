package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;

public class ServerReceiver extends Thread {
    BufferedReader in;
    PrintWriter out;
    Thread thread;
    Socket socket;
    public ServerReceiver(Socket socket){
        try{
            this.socket=socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread = new Thread(this);
    }
    public String getAnswer(){
        String s = "";
        try {
            while((s = in.readLine()) != null){
                return s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       return s;
    }
}

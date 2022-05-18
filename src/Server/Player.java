package Server;

import java.io.*;
import java.net.Socket;

public class Player extends Thread{
    String player;
    Socket socket;
    Game game;
    Thread thread;
    ObjectOutputStream out;
    ServerReceiver receiver;
    int amountOfQuestions;

    private String [] results;

    public Player(String player, Socket socket, Game game) {
        this.player = player;
        this.socket = socket;
        this.game = game;
        amountOfQuestions = game.amountOfQuestions;
        results = new String[amountOfQuestions + 1];
        receiver = new ServerReceiver(socket);

        try{
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread = new Thread(this);
    }

    public void sendMessageToPlayer(Object o){
        try {
            out.reset();
            out.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setResults(int questionNr, String correctOrFalse){
        results[0] = player;
        results[questionNr + 1] = correctOrFalse;
    }
    public String [] getResults(){
        return results;
    }


    @Override
    public void run() {
    }
}

package Client;

import Questions.Question;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

public class Receiver extends Thread {
    Thread thread;
    ObjectInputStream in;
    Socket socket;
    Client client;
    Object obj;
    public Receiver(Socket socket, Client client){
        this.socket = socket;
        this.client = client;

        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread = new Thread(this);
        thread.start();
    }

    public void run(){
        while(true) {

            try {
                while (((obj = in.readObject())!=null)) {
                    if(obj instanceof Properties) {
                        client.gui.setRemainingPanels(Integer.parseInt(((Properties) obj).getProperty("amountOfRows")),
                                Integer.parseInt(((Properties) obj).getProperty("amountOfQuestions")));
                        client.setProperties(Integer.parseInt(((Properties) obj).getProperty("amountOfRows")),
                                Integer.parseInt(((Properties) obj).getProperty("amountOfQuestions")));
                    }
                    if(obj instanceof List<?>) {
                        client.setCategoryQuestion((List<?>) obj);
                        client.gui.setContentPane(client.gui.gamePanel);
                        client.gui.repaint();
                        client.gui.revalidate();
                        client.gui.scorePanel.player1.setText("Player 1");
                        client.gui.scorePanel.player2.setText("Player 2");
                    }

                    if(obj instanceof Question) {
                        client.setCurrentQuestion((Question)obj);
                        client.gui.setContentPane(client.gui.gamePanel);
                        client.gui.repaint();
                        client.gui.revalidate();
                        client.gui.scorePanel.player1.setText("Player 1");
                        client.gui.scorePanel.player2.setText("Player 2");
                    }

                    else if(obj instanceof String []){
                        client.setResults((String[]) obj);
                    }

                    else if (obj instanceof String){
                        String s = (String) obj;
                        if (s.equalsIgnoreCase("Player 1")){
                            client.setPlayerID(1);
                        } else if (s.equalsIgnoreCase("Player 2")){
                            client.setPlayerID(2);
                        } else if (s.equalsIgnoreCase("Next Round")){
                            client.setCurrentRow();

                            if (client.playerID==1){
                                client.gui.scorePanel.player1.setText("<html><center>Player 1 <br> (Opponents turn)</center></html>");
                            } else {
                                client.gui.scorePanel.player2.setText("<html><center>Player 2 <br>(Opponents turn)</center></html>");
                            }

                        } else if(s.equalsIgnoreCase("wait")){

                            if (client.playerID==1){
                                client.gui.scorePanel.player1.setText("<html><center>Player 1 <br> (Opponents turn)</center></html>");
                            } else {
                                client.gui.scorePanel.player2.setText("<html><center>Player 2 <br>(Opponents turn)</center></html>");
                            }

                        } else if (s.contains("Score")) {
                            client.setScore(s);
                        } else if (s.equalsIgnoreCase("won") || s.equalsIgnoreCase("lost") ||
                        s.equalsIgnoreCase("tied")) {
                            client.setEndResult(s);
                        } else if (s.equalsIgnoreCase("start?")){
                            client.gui.scorePanel.player1.setText("Player 1");
                            client.gui.scorePanel.player2.setText("Player 2");
                            client.showStartButton();
                        } else if (s.equalsIgnoreCase("Correct!") || s.equalsIgnoreCase("Wrong answer!")) {
                            client.gui.gamePanel.question.setText(s);
                            Thread.sleep(1000);
                            client.resetButtonColor();
                            client.gui.setContentPane(client.gui.scorePanel);
                            client.gui.repaint();
                            client.gui.revalidate();
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException | InterruptedException | FontFormatException e) {
                e.printStackTrace();
            }
        }
    }
}


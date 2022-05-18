package Client;

import Questions.Question;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class Client extends Thread {
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    Receiver receiver;
    Question currentQuestion;
    Thread thread = new Thread(this);
    ClientGUI gui;
    List alternatives;
    List categories;
    int playerID;
    public int amountOfRows;
    public int amountOfQuestions;
    Color myGreen = new Color(154,205,50);
    Color myRed = new Color(220,20,60);

    public Client() throws IOException, FontFormatException {
        this.gui = new ClientGUI(this);
    }

    public void setPlayerID(int ID){
        playerID = ID;
        if(playerID == 1){
            gui.scorePanel.player1.setText("<html><center>" +" You are player 1 <br> waiting for player 2 to connect" + "</center></html>");
        } else if(playerID == 2){
            gui.scorePanel.player2.setText("<html><center>" + " You are player 2 <br> Waiting for player 1 to finish round" + "</center></html>");
        }
    }

    public void setProperties(int amountOfRows, int amountOfQuestions){
        this.amountOfRows = amountOfRows;
        this.amountOfQuestions = amountOfQuestions;
    }

    public void setResults(String[] s){
        if (playerID == 1){
            if (s[0].equals("Player 1")) {
                for (int i = 1; i < s.length; i++) {
                    if (s[i].equalsIgnoreCase("correct")) {
                        gui.scorePanel.currentRow.labels[i-1].setBackground(myGreen);
                    } else if (s[i].equalsIgnoreCase("false")) {
                        gui.scorePanel.currentRow.labels[i-1].setBackground(myRed);
                    }
                }
            } else {
                for (int i = 1; i <s.length ; i++) {
                    if(s[i].equalsIgnoreCase("correct")) {
                        gui.scorePanel.currentRow.labels[i+amountOfQuestions].setBackground(myGreen);
                    } else if (s[i].equalsIgnoreCase("false")){
                        gui.scorePanel.currentRow.labels[i+amountOfQuestions].setBackground(myRed);
                    }
                }
            }

        } else if (playerID == 2){
            if (s[0].equals("Player 2"))
            for (int i = 1; i <s.length ; i++) {
                if(s[i].equalsIgnoreCase("correct")) {
                    gui.scorePanel.currentRow.labels[i+amountOfQuestions].setBackground(myGreen);
                } else if (s[i].equalsIgnoreCase("false")){
                    gui.scorePanel.currentRow.labels[i+amountOfQuestions].setBackground(myRed);
                }
            } else {
                for (int i = 1; i < s.length; i++) {
                    if (s[i].equalsIgnoreCase("correct")) {
                        gui.scorePanel.currentRow.labels[i-1].setBackground(myGreen);
                    } else if (s[i].equalsIgnoreCase("false")) {
                        gui.scorePanel.currentRow.labels[i-1].setBackground(myRed);
                    }
                }
            }
        }
        gui.scorePanel.repaint();
        gui.scorePanel.revalidate();
    }

    public void setScore(String s){
        gui.scorePanel.score.setText(s.substring(5));
    }

    public void setCurrentRow(){
        gui.scorePanel.setCurrentRow();
    }

    public void setCurrentQuestion(Question q){
        currentQuestion = q;
        alternatives = q.getShuffledAlternatives();
        gui.scorePanel.currentRow.labels[amountOfQuestions].setText(currentQuestion.getCategory());
        gui.gamePanel.question.setText("<html><center>" + currentQuestion.getQuestion() + "</center></html>");
        gui.gamePanel.btn1.setText((String) alternatives.get(0));
        gui.gamePanel.btn2.setText((String) alternatives.get(1));
        gui.gamePanel.btn3.setText((String) alternatives.get(2));
        gui.gamePanel.btn4.setText((String) alternatives.get(3));
        gui.setCorrectAnswer(q.getCorrectAnswer());

        gui.repaint();
        gui.revalidate();
    }

    public void setCategoryQuestion (List<?> c){
        categories = c;
        gui.setCorrectAnswer("category");
        gui.gamePanel.question.setText("<html><center>" + "Choose your category" + "</center></html>");

        if(categories.size()>0) {
            gui.gamePanel.btn1.setText((String) categories.get(0));
        } else{
            gui.gamePanel.btn1.setText("");
        }if (categories.size()>1) {
            gui.gamePanel.btn2.setText((String) categories.get(1));
        } else {
            gui.gamePanel.btn2.setText("");
        }
        if(categories.size()>2) {
            gui.gamePanel.btn3.setText((String) categories.get(2));
        } else{
            gui.gamePanel.btn3.setText("");
        }
        if(categories.size()>3) {
            gui.gamePanel.btn4.setText((String) categories.get(3));
        } else{
            gui.gamePanel.btn4.setText("");
        }

        gui.repaint();
        gui.revalidate();
    }

    public void resetButtonColor(){
        gui.gamePanel.btn1.setBackground(new Color(59, 89, 182));
        gui.gamePanel.btn2.setBackground(new Color(59, 89, 182));
        gui.gamePanel.btn3.setBackground(new Color(59, 89, 182));
        gui.gamePanel.btn4.setBackground(new Color(59, 89, 182));
    }

    public void sendAnswer(String s){
        out.println(s);
    }

    public void setEndResult(String s){
        int btnNr = (JOptionPane.showConfirmDialog(null, "You " + s + "! New game?"));
        if (btnNr == JOptionPane.YES_OPTION) {
            try {
                socket = new Socket("localhost", 55555);
                receiver = new Receiver(socket, this);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else System.exit(0);
    }

    public void showStartButton(){
        gui.scorePanel.button.setVisible(true);
    }

    @Override
    public void run() {
        try{
            socket = new Socket("localhost", 55555);
            receiver = new Receiver(socket, this);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gui.setContentPane(gui.scorePanel);
        gui.repaint();
        gui.revalidate();
    }

    public static void main(String[]args) throws IOException, FontFormatException {
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        Client client = new Client();
    }
}

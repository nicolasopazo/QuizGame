package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ClientGUI extends JFrame implements ActionListener{

    Client client;
    GamePanel gamePanel;
    ScorePanel scorePanel;
    IntroPanel introPanel;
    String correctAnswer = "";

    public ClientGUI (Client client) throws IOException, FontFormatException {
      setTitle("Quiz Game");
      this.client = client;
      introPanel = new IntroPanel(this);
      this.setContentPane(introPanel);
      gamePanel = new GamePanel(this);
      scorePanel = new ScorePanel(this, 4, 4);

      pack();
      setVisible(true);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void setRemainingPanels(int amountOfRows, int amountOfQuestions) throws IOException, FontFormatException {
        scorePanel = new ScorePanel(this, amountOfRows, amountOfQuestions);
        repaint();
        revalidate();
        setContentPane(scorePanel);
  }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

      if(e.getSource().equals(gamePanel.btn1)){
          if(!gamePanel.btn1.getText().equals("")) {
              client.sendAnswer(gamePanel.btn1.getText());
              changeButtonColor(gamePanel.btn1);
          }
      }
      if(e.getSource().equals(gamePanel.btn2)){
          if(!gamePanel.btn2.getText().equals("")) {
              client.sendAnswer(gamePanel.btn2.getText());
              changeButtonColor(gamePanel.btn2);
          }
      }
      if(e.getSource().equals(gamePanel.btn3)){
          if(!gamePanel.btn3.getText().equals("")) {
              client.sendAnswer(gamePanel.btn3.getText());
              changeButtonColor(gamePanel.btn3);
          }
      }
      if(e.getSource().equals(gamePanel.btn4)){
          if(!gamePanel.btn4.getText().equals("")) {
              client.sendAnswer(gamePanel.btn4.getText());
              changeButtonColor(gamePanel.btn4);
          }
      }
      if(e.getSource().equals(introPanel.button)){
          client.thread.start();
      }
      if(e.getSource().equals(scorePanel.button)){
          client.sendAnswer("start");
          client.gui.scorePanel.button.setVisible(false);
      }
    }
    static Font neonFont() throws IOException, FontFormatException {
        Font neonFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/NEONLEDlight.otf")).deriveFont(18f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(neonFont);
        return neonFont;
    }
    static Font lemonFont() throws IOException, FontFormatException {
        Font lemonFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/LEMONMILK-Medium.otf")).deriveFont(14f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(lemonFont);
        return lemonFont;
    }

    public void changeButtonColor(JButton button){
        if (button.getText().equalsIgnoreCase(correctAnswer)) {
            button.setBackground(new Color(88, 214, 141));
            revalidate();
            repaint();
        } else if (!button.getText().equalsIgnoreCase(correctAnswer) && !correctAnswer.equals("category")) {
            button.setBackground(new Color(192, 57, 43));
            revalidate();
            repaint();
            correctAnswer = "";
        }
    }
}

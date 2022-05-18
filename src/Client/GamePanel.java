package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Client.ClientGUI.*;

public class GamePanel extends JPanel {
    JButton btn1;
    JButton btn2;
    JButton btn3;
    JButton btn4;
    JLabel question;
    public GamePanel(ActionListener a) throws IOException, FontFormatException {
        setLayout(new BorderLayout());
        JPanel bottomPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        middlePanel.setPreferredSize(new Dimension(500,100));
        middlePanel.setBackground(new Color(40, 55, 71));
        middlePanel.setLayout(new GridLayout());
        bottomPanel.setPreferredSize(new Dimension(250,250));
        bottomPanel.setBackground(new Color(40, 55, 71));
        bottomPanel.setLayout(new GridLayout(2,2,4,4));

        btn1 = new JButton("1");
        btn1.addActionListener(a);
        btn1.setBackground(new Color(59, 89, 182));
        btn1.setForeground(Color.WHITE);
        btn1.setFocusPainted(false);
        btn1.setFont(neonFont());

        btn2 = new JButton("2");
        btn2.addActionListener(a);
        btn2.setBackground(new Color(59, 89, 182));
        btn2.setForeground(Color.WHITE);
        btn2.setFocusPainted(false);
        btn2.setFont(neonFont());

        btn3 = new JButton("3");
        btn3.addActionListener(a);
        btn3.setBackground(new Color(59, 89, 182));
        btn3.setForeground(Color.WHITE);
        btn3.setFocusPainted(false);
        btn3.setFont(neonFont());

        btn4 = new JButton("4");
        btn4.addActionListener(a);
        btn4.setBackground(new Color(59, 89, 182));
        btn4.setForeground(Color.WHITE);
        btn4.setFocusPainted(false);
        btn4.setFont(neonFont());


        question = new JLabel("Fråga");
        question.setBackground(new Color(40, 55, 71));
        question.setForeground(Color.WHITE);
        question.setFont(lemonFont());
        question.setOpaque(true);
        question.setHorizontalAlignment(SwingConstants.CENTER);

        middlePanel.add(question);
        bottomPanel.add(btn1);    bottomPanel.add(btn2);      bottomPanel.add(btn3);      bottomPanel.add(btn4);
        add(bottomPanel,BorderLayout.SOUTH);
        add(middlePanel,BorderLayout.CENTER);
    }
}

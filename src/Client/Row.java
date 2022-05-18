package Client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;

import static Client.ClientGUI.lemonFont;
import static Client.ClientGUI.neonFont;

public class Row extends JPanel {

    JLabel [] labels;
    public Row(int amountOfQuestions) throws IOException, FontFormatException {
        setPreferredSize(new Dimension(500,45));
        setLayout(new GridLayout(1,7,4,0));
        setBackground(new Color(40, 55, 71));
        setBorder(BorderFactory.createEmptyBorder(5,2,5,2));


        labels = new JLabel[(amountOfQuestions * 2 + 1)];

        for (int i = 0; i < labels.length ; i++) {
            LineBorder line = new LineBorder(new Color(100,149,237),2,true);
            labels[i] = new JLabel();
            labels[i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            labels[i].setBackground(new Color(40, 55, 71));
            float size = 10;
            labels[i].setFont(lemonFont().deriveFont(size));
            labels[i].setForeground(Color.white );
            labels[i].setBorder(line);
            if(i==amountOfQuestions){
                labels[(amountOfQuestions)].setText("??");
            }
            labels[i].setOpaque(true);
            this.add(labels[i]);
        }
    }
}

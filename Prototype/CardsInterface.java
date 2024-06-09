
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CardsInterface {

    static String c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13;
    static JButton[] buttons;
    static void cards(JFrame frame) {

        c1 = c2 = c3 = c4 = c5= c6 = c7 = c8 = c9 = c10 = c11 = c12 = c13 = "";

        JButton b1 = new JButton(c1);
        JButton b2 = new JButton(c2);
        JButton b3 = new JButton(c3);
        JButton b4 = new JButton(c4);
        JButton b5 = new JButton(c5);
        JButton b6 = new JButton(c6);
        JButton b7 = new JButton(c7);
        JButton b8 = new JButton(c8);
        JButton b9 = new JButton(c9);
        JButton b10 = new JButton(c10);
        JButton b11 = new JButton(c11);
        JButton b12 = new JButton(c12);
        JButton b13 = new JButton(c13);


        b1.setBounds(10, 520, 60, 85);
        b2.setBounds(71, 520, 60, 85);
        b3.setBounds(132, 520, 60, 85);
        b4.setBounds(193, 520, 60, 85);
        b5.setBounds(254, 520, 60, 85);
        b6.setBounds(315, 520, 60, 85);
        b7.setBounds(376, 520, 60, 85);
        b8.setBounds(437, 520, 60, 85);
        b9.setBounds(498, 520, 60, 85);
        b10.setBounds(559, 520, 60, 85);
        b11.setBounds(620, 520, 60, 85);
        b12.setBounds(681, 520, 60, 85);
        b13.setBounds(742, 520, 60, 85);

        JButton[] buttons = {b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13};

        frame.add(b1);frame.add(b2);frame.add(b3);
        frame.add(b4);frame.add(b5);frame.add(b6);
        frame.add(b7);frame.add(b8);frame.add(b9);
        frame.add(b10);frame.add(b11);frame.add(b12);
        frame.add(b13);

        buttonActionListener(b1);buttonActionListener(b2);buttonActionListener(b3);
        buttonActionListener(b4);buttonActionListener(b5);buttonActionListener(b6);
        buttonActionListener(b7);buttonActionListener(b8);buttonActionListener(b9);
        buttonActionListener(b10);buttonActionListener(b11);buttonActionListener(b12);
        buttonActionListener(b13);


    }

    static private void buttonActionListener(JButton b) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                b.setEnabled(false);
            }
        });
    }

}

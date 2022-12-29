package ru.artem.roflandictionary.swing;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class RoflamFrame extends JFrame {

    private final JLabel word;
    private final JLabel result;
    @Getter
    @Setter
    private JTextField tname;
    private final JButton sub;

    public RoflamFrame() {
        setTitle("dictionary");
        setBounds(440, 40, 250, 250);
        setLocation(440, 4550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        Container c = getContentPane();
        c.setLayout(null);

        word = new JLabel("dictionary");
        word.setFont(new Font("Arial", Font.PLAIN, 20));
        word.setSize(200, 30);
        word.setLocation(10, 30);
        c.add(word);

        result = new JLabel("");
        result.setFont(new Font("Arial", Font.PLAIN, 10));
        result.setSize(200, 30);
        result.setLocation(10, 200);
        c.add(result);


        tname = new JTextField();
        tname.setFont(new Font("Arial", Font.PLAIN, 15));
        tname.setSize(190, 20);
        tname.setLocation(10, 100);
        c.add(tname);

        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(10, 150);
        c.add(sub);

        setVisible(true);
    }

    public void setResult(String s){
        this.result.setText(s);
    }

    public void setWord(String word){
        this.word.setText(word);
    }

    public String getWord(){
        return word.getText();
    }

    public void setActionListener(ActionListener actionListener){
        sub.addActionListener(actionListener);
    }

}

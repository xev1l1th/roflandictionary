package ru.artem.roflandictionary.swing;

import lombok.Getter;
import lombok.Setter;
import ru.artem.roflandictionary.data.Word;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class RoflamFrame extends JFrame {

    private final JLabel word;
    private final JLabel extra;
    private final JLabel result;
    @Getter
    @Setter
    private JTextField tname;
    private final JButton sub;
    @Getter
    private final JButton iWasRight;
    @Getter
    @Setter
    private Word wordToTranslate;

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

        extra = new JLabel("dictionary");
        extra.setFont(new Font("Arial", Font.PLAIN, 15));
        extra.setSize(200, 30);
        extra.setLocation(10, 60);
        c.add(extra);

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

        sub = new JButton("ТРАНСЛЕЙТ");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(10, 150);
        c.add(sub);

        iWasRight = new JButton("Я БЫЛ ПРАВ");
        iWasRight.setFont(new Font("Arial", Font.PLAIN, 15));
        iWasRight.setSize(100, 20);
        iWasRight.setLocation(150, 150);
        iWasRight.setVisible(false);
        c.add(iWasRight);

        setVisible(true);
    }

    public void setResult(String s) {
        this.result.setText(s);
    }

    public void setWord(String word) {
        this.word.setText(word);
    }

    public String getWord() {
        return word.getText();
    }

    public void setExtra(String s) {
        this.extra.setText(s);
    }

    public void setActionListener(ActionListener actionListener) {
        sub.addActionListener(actionListener);
    }

    public void setiWasRightAction(ActionListener actionListener){
        iWasRight.addActionListener(actionListener);
    }
}

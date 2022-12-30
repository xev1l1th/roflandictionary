package ru.artem.roflandictionary.service;

import org.springframework.stereotype.Service;
import ru.artem.roflandictionary.swing.RoflamFrame;

import javax.swing.*;

@Service
public class GuiCreator {

    private final DictionaryService dictionaryService;
    private final ActionPerformDelegator actionPerformDelegator;

    public GuiCreator(DictionaryService dictionaryService, ActionPerformDelegator actionPerformDelegator) {
        this.dictionaryService = dictionaryService;
        this.actionPerformDelegator = actionPerformDelegator;
    }

    private RoflamFrame mainFrame;


    public void createAndShowGui() {
        mainFrame = new RoflamFrame();
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String randomWord = dictionaryService.getRandomWord();
        mainFrame.setWord(randomWord);
        mainFrame.setActionListener(event -> actionPerformDelegator.delegateActionPerform(event, mainFrame));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void setVisible(boolean f){
        mainFrame.setVisible(f);
        mainFrame.setAlwaysOnTop(true);
    }

}

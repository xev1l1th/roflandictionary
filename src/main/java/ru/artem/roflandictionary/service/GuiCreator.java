package ru.artem.roflandictionary.service;

import org.springframework.stereotype.Service;
import ru.artem.roflandictionary.data.Word;
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
        Word randomWord = dictionaryService.getRandomWord();
        mainFrame.setWordToTranslate(randomWord);
        mainFrame.setWord(randomWord.getValue());
        mainFrame.setExtra(randomWord.getExtra());
        mainFrame.setActionListener(event -> actionPerformDelegator.delegateActionPerform(mainFrame));
        mainFrame.setiWasRightAction(event -> actionPerformDelegator.delegateIWasRightAction(mainFrame));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void setVisible(boolean f) {
        mainFrame.setVisible(f);
        mainFrame.setAlwaysOnTop(true);
    }

}

package ru.artem.roflandictionary.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
@Slf4j
public class DictionaryLauncher {

    private final GuiCreator guiCreator;

    @Getter
    private volatile boolean isLaunched = false;

    public DictionaryLauncher(GuiCreator guiCreator) {
        this.guiCreator = guiCreator;
    }

    public void launch() {
        if (!isLaunched) {
            try {
                SwingUtilities.invokeLater(guiCreator::createAndShowGui);
            } catch (Exception e) {
                log.error("error", e);
            }
            isLaunched = true;
        } else {
            guiCreator.setVisible(true);
        }

    }

    public void show() {
        if (isLaunched) {
            guiCreator.setVisible(true);
        }
    }
}

package ru.artem.roflandictionary.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.artem.roflandictionary.swing.RoflamFrame;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class ActionPerformDelegator {

    private final ScheduledExecutorService service;
    private final DictionaryService dictionaryService;

    private final AtomicInteger efforts = new AtomicInteger(0);

    public ActionPerformDelegator(ScheduledExecutorService service, DictionaryService dictionaryService) {
        this.service = service;
        this.dictionaryService = dictionaryService;
    }

    public void delegateActionPerform(ActionEvent event, RoflamFrame mainFrame){
        String tname = mainFrame.getTname().getText();
        boolean b = dictionaryService.compareDefinition(mainFrame.getWord(), tname);
        if (b) {
            efforts.set(0);
            mainFrame.setVisible(false);
            log.info(tname);
            //schedule thread that will setVisible window this a new word for translation
            service.schedule(() -> {
                String randomWord1 = dictionaryService.getRandomWord();
                mainFrame.setWord(randomWord1);
                mainFrame.setVisible(true);
                mainFrame.setResult("");
                mainFrame.getTname().setText("");
                mainFrame.doLayout();
                mainFrame.setAlwaysOnTop(true);
            }, 120, TimeUnit.SECONDS);
        } else {
            int i = efforts.incrementAndGet();
            if(i >= 3){
                List<String> definition = dictionaryService.getDefinition(mainFrame.getWord());
                mainFrame.setResult(listToString(definition));
            } else {
                mainFrame.setResult("НЕТ!");
            }
        }
    }
    private String listToString(List<String> list){
        StringBuilder stringBuilder = new StringBuilder();
        for(String s: list){
            stringBuilder.append(s);
            stringBuilder.append(" / ");
        }
        return stringBuilder.toString();
    }

}

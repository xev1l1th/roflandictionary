package ru.artem.roflandictionary.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.artem.roflandictionary.data.Word;
import ru.artem.roflandictionary.swing.RoflamFrame;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class ActionPerformDelegator {

    private final ScheduledExecutorService service;
    private final DictionaryService dictionaryService;
    private final WordService wordService;

    private ScheduledFuture<?> scheduledFuture;

    private final AtomicInteger efforts = new AtomicInteger(0);

    public ActionPerformDelegator(ScheduledExecutorService service, DictionaryService dictionaryService, WordService wordService) {
        this.service = service;
        this.dictionaryService = dictionaryService;
        this.wordService = wordService;
    }

    public void delegateActionPerform(RoflamFrame mainFrame){
        String tname = mainFrame.getTname().getText();
        boolean b = dictionaryService.compareDefinition(mainFrame.getWord(), tname);
        if (b) {
            efforts.set(0);
            mainFrame.setVisible(false);
            log.info(tname);
            mainFrame.getIWasRight().setVisible(false);
            //schedule thread that will setVisible window this a new word for translation
            scheduledFuture = service.schedule(() -> {
                Word randomWord1 = dictionaryService.getRandomWord();
                mainFrame.setWordToTranslate(randomWord1);
                mainFrame.setWord(randomWord1.getValue());
                mainFrame.setExtra(String.join("\n", randomWord1.getExtra()));
                mainFrame.setVisible(true);
                mainFrame.setResult("");
                mainFrame.getTname().setText("");
                mainFrame.doLayout();
                mainFrame.setAlwaysOnTop(true);
            }, 20, TimeUnit.SECONDS);
        } else {
            int i = efforts.incrementAndGet();
            if(i >= 3){
                List<String> definition = dictionaryService.getDefinition(mainFrame.getWord());
                mainFrame.setResult(listToString(definition));
                String text = mainFrame.getTname().getText();
                if(text != null && !text.isEmpty()) {
                    mainFrame.getIWasRight().setVisible(true);
                }
            } else {
                mainFrame.setResult("НЕТ!");
            }
        }
    }

    //add new right definition
    public void delegateIWasRightAction(RoflamFrame roflamFrame){
        String rigthDefinition = roflamFrame.getTname().getText();
        if(rigthDefinition != null && !rigthDefinition.isEmpty()) {
            Word wordToTranslate = roflamFrame.getWordToTranslate();
            wordToTranslate.addDefinition(rigthDefinition);
            wordService.saveWord(wordToTranslate);
        }
    }

    private String listToString(List<String> list){
        return String.join(" / ", list);
    }

    public Boolean isScheduledTaskDone(){
        if(scheduledFuture == null) return null;
        return scheduledFuture.isDone();
    }

}

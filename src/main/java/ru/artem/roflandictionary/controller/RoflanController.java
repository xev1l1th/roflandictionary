package ru.artem.roflandictionary.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.artem.roflandictionary.service.ActionPerformDelegator;
import ru.artem.roflandictionary.service.Dictionary;
import ru.artem.roflandictionary.service.DictionaryLauncher;
import ru.artem.roflandictionary.service.DictionaryUpdater;

@Controller
@Slf4j
public class RoflanController {

    private final DictionaryLauncher dictionaryLauncher;
    private final Dictionary storedDictionary;
    private final ActionPerformDelegator delegator;

    //mb use better composite
    private final DictionaryUpdater<MultipartFile> dictionaryUpdater;

    public RoflanController(DictionaryLauncher dictionaryLauncher, Dictionary storedDictionary, ActionPerformDelegator delegator, DictionaryUpdater<MultipartFile> dictionaryUpdater) {
        this.dictionaryLauncher = dictionaryLauncher;
        this.storedDictionary = storedDictionary;
        this.delegator = delegator;
        this.dictionaryUpdater = dictionaryUpdater;
    }

    @GetMapping
    @ResponseBody
    public String launch(){
        if(!dictionaryLauncher.isLaunched()) {
            if(!storedDictionary.isConfigured()){
                return "repo is not configured, go to /configure and upload the dictionary";
            }
            dictionaryLauncher.launch();
        }
        return "launched";
    }

    @GetMapping("/configure")
    public String get(){
        return "hello";
    }

    @PostMapping("/configure")
    @SneakyThrows
    @ResponseBody
    public String configure(MultipartFile file){
        dictionaryUpdater.update(file);
        return "configured";
    }

    @GetMapping("/show")
    @ResponseBody
    public String show(){
        if(dictionaryLauncher.isLaunched() && (delegator.isScheduledTaskDone() == null || delegator.isScheduledTaskDone())){
            dictionaryLauncher.show();
        }
        return "showed";
    }

    @GetMapping("done")
    @ResponseBody
    public String isTaskRunning(){
        Boolean f = delegator.isScheduledTaskDone();
        if(f == null) return "task has not started yet";
        return delegator.isScheduledTaskDone() ? "DONE" : "RUNNING";
    }

}

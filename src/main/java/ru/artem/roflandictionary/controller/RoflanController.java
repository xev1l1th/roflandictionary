package ru.artem.roflandictionary.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.artem.roflandictionary.service.ActionPerformDelegator;
import ru.artem.roflandictionary.service.DictionaryLauncher;
import ru.artem.roflandictionary.service.DictionaryRepo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

@Controller
@Slf4j
public class RoflanController {

    private final DictionaryLauncher dictionaryLauncher;
    private final DictionaryRepo repo;
    private final ActionPerformDelegator delegator;

    public RoflanController(DictionaryLauncher dictionaryLauncher, DictionaryRepo repo, ActionPerformDelegator delegator) {
        this.dictionaryLauncher = dictionaryLauncher;
        this.repo = repo;
        this.delegator = delegator;
    }

    @GetMapping
    @ResponseBody
    public String launch(){
        if(!dictionaryLauncher.isLaunched()) {
            if(!repo.isConfigured()){
                return "repo is not configured, go to /configure and upload the dictionary";
            }
            dictionaryLauncher.launch();
        }
        return "launched";
    }

    @GetMapping("/configure")
    public String get(Model model){
        return "hello";
    }

    @PostMapping("/configure")
    @SneakyThrows
    @ResponseBody
    public String configure(MultipartFile file){
        InputStream inputStream = file.getInputStream();
        Stream<String> lines = new BufferedReader(new InputStreamReader(inputStream)).lines();
        repo.updateDictionary(lines.toList());
        inputStream.close();
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

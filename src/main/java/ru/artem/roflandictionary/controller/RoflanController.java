package ru.artem.roflandictionary.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.artem.roflandictionary.service.DictionaryLauncher;

@RestController
@Slf4j
public class RoflanController {

    private final DictionaryLauncher dictionaryLauncher;

    public RoflanController(DictionaryLauncher dictionaryLauncher) {
        this.dictionaryLauncher = dictionaryLauncher;
    }

    @GetMapping
    public String rest(){
        if(!dictionaryLauncher.isLaunched()) {
            dictionaryLauncher.launch();
        }
        return "launched";
    }

    @GetMapping("/show")
    public String show(){
        if(dictionaryLauncher.isLaunched()){
            dictionaryLauncher.show();
        }
        return "showed";
    }

}

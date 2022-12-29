package ru.artem.roflandictionary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@Slf4j
public class RoflanDictionaryApplication {

    public static void main(String[] args) {
       new SpringApplicationBuilder(RoflanDictionaryApplication.class).headless(false).run(args);
    }

}

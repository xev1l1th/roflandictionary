package ru.artem.roflandictionary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@Slf4j
@EnableCaching
public class RoflanDictionaryApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RoflanDictionaryApplication.class).headless(false).run(args);
    }

}

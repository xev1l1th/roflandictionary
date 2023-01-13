package ru.artem.roflandictionary.service;

import lombok.SneakyThrows;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ru.artem.roflandictionary.data.Word;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

@Service
public class ExplanationExtractor implements StringExtractor {

    @Override
    public List<String> extract(String s) {
        Stack<Integer> stack = new Stack<>();
        //all indexes of ( and )
        List<Pair<Integer, Character>> pairs = Pattern
                .compile("[(,)]")
                .matcher(s)
                .results()
                .map(MatchResult::start)
                .map(i -> Pair.of(i, s.charAt(i)))
                .toList();
        List<String> extras = new ArrayList<>();
        //extract all in ()
        for (Pair<Integer, Character> pair : pairs) {
            switch (pair.getSecond()) {
                case '(' -> stack.push(pair.getFirst());
                case ')' -> {
                    if (stack.isEmpty()) {
                        System.out.println("stack is not correct");
                        return Collections.EMPTY_LIST;
                    }
                    Integer pop = stack.pop();
                    extras.add(s.substring(pop + 1, pair.getFirst()));
                }
            }
        }
        return extras;
    }
}

package ru.artem.roflandictionary.service;

public interface DictionaryParser<T, V> {
    V parse(T value);
}

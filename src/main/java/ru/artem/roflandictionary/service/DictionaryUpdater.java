package ru.artem.roflandictionary.service;

public interface DictionaryUpdater<T> {
    void update(T data);

    boolean isApplicable(T data);
}

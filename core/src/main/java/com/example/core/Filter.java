package com.example.core;

public interface Filter<T> {

    void addFilter(String key, T param);

    T getFilter(String key);


}

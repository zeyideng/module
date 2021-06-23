package com.excample.module.core;

import java.util.List;

public interface Filter<T> {

    void addFilter(String key, T param);

    T getFilter(String key);


}

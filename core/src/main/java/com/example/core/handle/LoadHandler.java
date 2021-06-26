package com.example.core.handle;

public interface LoadHandler<T, K> {

    T load(K param);

}

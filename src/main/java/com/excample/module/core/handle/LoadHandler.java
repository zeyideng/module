package com.excample.module.core.handle;

public interface LoadHandler<T, K> {

    T load(K param);

}

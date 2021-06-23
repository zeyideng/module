package com.excample.module.core;

public interface RequestProcessor<T> {

    Object processor(ModuleContext<T> context);

}

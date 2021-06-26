package com.example.core;

public interface RequestProcessor<T> {

    Object processor(ModuleContext<T> context);

}

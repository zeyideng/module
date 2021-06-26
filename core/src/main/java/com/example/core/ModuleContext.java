package com.example.core;


import com.example.common.enums.ExecuteType;

public class ModuleContext<T> {

    private ExecuteType executeType;

    T data;

    public ExecuteType getExecuteType() {
        return executeType;
    }

    public void setExecuteType(ExecuteType executeType) {
        this.executeType = executeType;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}

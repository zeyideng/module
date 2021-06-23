package com.excample.module.core;

import com.excample.module.controller.ClassParam;
import com.excample.module.core.handle.DestroyHandler;
import com.excample.module.core.handle.GetHandler;
import com.excample.module.core.handle.LoadHandler;
import com.excample.module.enums.ExecuteType;
import org.springframework.beans.factory.annotation.Autowired;

public class HttpRequestProcessor implements RequestProcessor<ClassParam> {

    @Autowired
    private GetHandler getHandler;

    @Autowired
    private LoadHandler loadHandler;

    @Autowired
    private DestroyHandler destroyHandler;

    @Override
    public Object processor(ModuleContext<ClassParam> context) {
        ClassParam param = context.getData();
        String module = param.getModule();
        Object res = null;
        ExecuteType executeType = context.getExecuteType();
        if (executeType == null) {
            throw new IllegalArgumentException("类型不能为空");
        }
        if (ExecuteType.GET.equals(executeType)) {
            res = getHandler.doGet(module);
        } else if (ExecuteType.DESTROY.equals(executeType)) {
            destroyHandler.destroy(module);
        } else if (ExecuteType.LOAD.equals(executeType)) {
            loadHandler.load(context);
        }
        return res;
    }
}

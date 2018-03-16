package com.pcq;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by pcq on 2017/5/24.
 */
public class WMethod {
    public String className;
    public String methodName;
    public String methodDesc;

    public Method method;
    public List<WParameter> parameters;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<WParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<WParameter> parameters) {
        this.parameters = parameters;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(String methodDesc) {
        this.methodDesc = methodDesc;
    }
}

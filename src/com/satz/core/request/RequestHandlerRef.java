package com.satz.core.request;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class RequestHandlerRef{
    Object requestHandler;
    Method requestMethod;
    String requestPath;
    Class [] parameterTypes;
    Annotation [][] paramAnnotations;

    public RequestHandlerRef(Object requestHandler, Method requestMethod, String requestPath, Class [] parameterTypes, Annotation [][] paramAnnotations) {
        this.requestHandler = requestHandler;
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
        this.parameterTypes = parameterTypes;
        this.paramAnnotations = paramAnnotations;
    }

    public Object getRequestHandler() {
        return requestHandler;
    }

    public Method getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public Annotation[][] getParamAnnotations() {
        return paramAnnotations;
    }
}

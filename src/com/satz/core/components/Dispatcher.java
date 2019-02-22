package com.satz.core.components;

import com.satz.core.annots.RequestParam;
import com.satz.core.request.RequestHandlerRef;
import com.satz.core.request.RequestHandlerScanner;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class Dispatcher {

    public static void routeReq(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("Processing request.." +         req.getPathInfo());
        RequestHandlerRef handlerRef = RequestHandlerScanner.getInstance().getRequestHandler(req.getPathInfo());

        if(handlerRef!=null){
            try {
                Object[] args = generateArgs(handlerRef,req);
                Object res = handlerRef.getRequestMethod().invoke(handlerRef.getRequestHandler(),args);
                if(res instanceof String) {
                    resp.getOutputStream().println((String) res);
                }else{
                    ObjectMapper mapper =  new ObjectMapper();
                    mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
                    resp.getOutputStream().println(mapper.writeValueAsString(res));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static Object[] generateArgs(RequestHandlerRef handlerRef, HttpServletRequest req) {
        int noOfArgs = handlerRef.getParameterTypes().length;
        Object[] args = new Object[noOfArgs];

        for(int i =0;i< noOfArgs;i++){
            args[i] =null;
            for(int j=0;j<handlerRef.getParamAnnotations()[i].length;j++){
                Annotation a = handlerRef.getParamAnnotations()[i][j];
                if(a instanceof RequestParam){
                    String paramValue = req.getParameter(((RequestParam)a).name());
                    args[i] = paramValue;
                    break;
                }
            }
        }

        return args;
    }
}

package com.satz.core.request;

import com.satz.core.annots.RequestMapping;
import com.satz.core.annots.RestController;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestHandlerScanner {

    static RequestHandlerScanner scanner =  new RequestHandlerScanner();
    private Map<String,Object> restControllers = new HashMap<>();
    private Map<String,RequestHandlerRef> paths = new HashMap<>();

    private RequestHandlerScanner(){}

    public static RequestHandlerScanner getInstance(){
        return scanner;
    }

    public void scanRequestHandler() {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));

        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents("");
        for (BeanDefinition bd : candidateComponents){
            System.out.println(bd.getBeanClassName());
            try {
                Class a = Class.forName(bd.getBeanClassName());
                for(Method m : a.getMethods()){
                    if(m.isAnnotationPresent(RequestMapping.class)){
                        System.out.println(m.getName());
                        if(!restControllers.containsKey(bd.getBeanClassName())){
                            restControllers.put(bd.getBeanClassName(), Class.forName(bd.getBeanClassName()).newInstance());
                        }
                        paths.put(m.getAnnotation(RequestMapping.class).path(),
                                new RequestHandlerRef(restControllers.get(bd.getBeanClassName()),m,m.getAnnotation(RequestMapping.class).path(),
                                m.getParameterTypes(), m.getParameterAnnotations()));
                        for(int i=0;i< m.getParameterTypes().length;i++){
                            System.out.println(m.getParameterTypes()[i]);
                            System.out.println(m.getParameterAnnotations()[i].length);
                            for(int j=0;j<m.getParameterAnnotations()[i].length;j++){
                                System.out.println(m.getParameterAnnotations()[i][j]);
                            }
                        }
                        System.out.println(Arrays.toString(m.getParameterTypes()));
                        System.out.println((m.getParameterAnnotations().length));
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }


        }

        for (String path: paths.keySet()){
            System.out.println("Key:"+ path + " - Value:"+ paths.get(path));
        }

    }

    public RequestHandlerRef getRequestHandler(String path){
        return paths.get(path);
    }



}

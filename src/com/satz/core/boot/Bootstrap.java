package com.satz.core.boot;

import com.satz.core.request.RequestHandlerScanner;
import com.satz.core.components.InterceptorServlet;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.util.Optional;

public class Bootstrap {

    public static final Optional<String> port = Optional.ofNullable(System.getenv("PORT"));
    public static final String INTERCEPTOR_SERVLET = "InterceptorServlet";

    public static void run() throws Exception{
        System.out.println("Starting tomcat...");

        String contextPath = "/";
        String appBase = ".";

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.valueOf(port.orElse("8090") ));
        tomcat.getHost().setAppBase(appBase);
        tomcat.addWebapp(contextPath, appBase);
        Context context = tomcat.addContext(contextPath, appBase);

        tomcat.addServlet(contextPath, INTERCEPTOR_SERVLET, new InterceptorServlet());
        context.addServletMappingDecoded("/*", INTERCEPTOR_SERVLET);

        RequestHandlerScanner.getInstance().scanRequestHandler();

        tomcat.start();
        tomcat.getServer().await();
        System.out.println("Started tomcat...");
    }
}

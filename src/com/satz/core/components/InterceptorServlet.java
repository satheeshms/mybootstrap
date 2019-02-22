package com.satz.core.components;

import com.satz.core.request.RequestHandlerRef;
import com.satz.core.request.RequestHandlerScanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@WebServlet(
        name = "InterceptorServlet",
        urlPatterns = {"/*"}
)

public class InterceptorServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher.routeReq(req,resp);
    }
}
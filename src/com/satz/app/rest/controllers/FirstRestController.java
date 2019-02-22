package com.satz.app.rest.controllers;

import com.satz.app.test.User;
import com.satz.core.annots.RequestMapping;
import com.satz.core.annots.RequestParam;
import com.satz.core.annots.RestController;

@RestController
public class FirstRestController {

    @RequestMapping(
            path = "/first"
    )
    public String hello(){

        return "hello from FirstRestController";
    }

    @RequestMapping(
            path = "/first/two"
    )
    public String hello2(@RequestParam(name="param1") String param,String param2, @RequestParam(name="param2") String param3) {
        return "Param 1:" + param + ", Param2:"+param3 ;
    }

    @RequestMapping(
            path = "/third"
    )
    public User hello3(){

        return new User("Satheesh", 35);
    }


}

package com.satz.app.rest.controllers;

import com.satz.app.test.User;
import com.satz.core.annots.RequestMapping;
import com.satz.core.annots.RequestParam;
import com.satz.core.annots.RestController;

@RestController
public class SecondRestController {

    @RequestMapping(
            path = "/second"
    )
    public String hello(){

        return "hello from FirstRestController";
    }


}

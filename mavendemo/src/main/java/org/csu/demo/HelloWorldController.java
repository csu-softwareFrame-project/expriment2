package org.csu.demo;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {

    @RequestMapping("info")
    public String info() {
        return "Hello world!!";
    }

}

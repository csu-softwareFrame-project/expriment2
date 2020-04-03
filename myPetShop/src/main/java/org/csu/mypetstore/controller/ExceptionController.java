package org.csu.mypetstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @desciption:目录相关跳转
 * @Date:2020.4.1
 */

@Controller
@RequestMapping("/error")
public class ExceptionController {

    //利用springboot精准匹配的原则处理404
    @RequestMapping("*")
    public String handle404() {
        return "common/error";
    }
}

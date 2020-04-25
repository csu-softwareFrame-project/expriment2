package org.csu.mypetstore.controller;

import org.csu.mypetstore.service.RobotService;
import org.junit.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {
    private RobotService robotService = new RobotService();

    @GetMapping("/response")
    public String response(@RequestParam String text){
        System.out.println(text);
        String response = robotService.resp(text);
        System.out.println(response);
        return response;
    }
}

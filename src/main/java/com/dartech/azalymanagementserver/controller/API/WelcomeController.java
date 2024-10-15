package com.dartech.azalymanagementserver.controller.API;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/welcome")
public class WelcomeController {

    @GetMapping("/hello")
    public String sayHello() {
       return "Hello mister";
    };

    @GetMapping("/bye")
    public String sayBye() {
        return "Bye mister";
    };
}

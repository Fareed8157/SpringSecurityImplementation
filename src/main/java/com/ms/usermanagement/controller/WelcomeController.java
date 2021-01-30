package com.ms.usermanagement.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${app.url}")
@Validated
public class WelcomeController {

    @Value("${app.title}")
    private String appTitle;

    @GetMapping("/welcome")
    public String welcome() {
        return "<center><h1 style='margin-top:200px'> Welcome to" + " " +appTitle+" Application Service </h1></center>";
    }
}

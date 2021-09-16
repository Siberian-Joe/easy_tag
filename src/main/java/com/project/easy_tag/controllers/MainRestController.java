package com.project.easy_tag.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class MainRestController {

    @GetMapping
    public String main() {
        return "index";
    }
}

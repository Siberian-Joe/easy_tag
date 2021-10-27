package com.project.easy_tag.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
//@PreAuthorize("hasAuthority('USER')")
public class CompanyController {
    public String company() {
        return "company";
    }
}

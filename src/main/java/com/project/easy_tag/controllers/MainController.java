package com.project.easy_tag.controllers;

import com.project.easy_tag.domains.Company;
import com.project.easy_tag.domains.User;
import com.project.easy_tag.repositories.CompanyRepository;
import com.project.easy_tag.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class MainController {
    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping
    public String main(@RequestParam String name, Model model) {

        model.addAttribute("frontendCompany", companyRepository.findByName(name));
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
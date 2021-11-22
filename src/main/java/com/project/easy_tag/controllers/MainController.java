package com.project.easy_tag.controllers;

import com.project.easy_tag.domains.Company;
import com.project.easy_tag.domains.User;
import com.project.easy_tag.repositories.CompanyRepository;
import com.project.easy_tag.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequestMapping
public class MainController {
    @Value("${spring.profiles.active}")
    private String devMode;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping
    public String main(@RequestParam(required = false) String name, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        HashMap<Object, Object> data = new HashMap<>();
        User user = null;

        if(userDetails != null) {
            user = userRepository.findByEmail(userDetails.getUsername());
            user.setPassword(null);
        }

        data.put("profile", user);

        Company company = companyRepository.findByName(name);

        if(company == null)
            data.put("company", companyRepository.findById("6167193e6a5709742542cfc7").orElseThrow());
        else
            data.put("company", company);

        model.addAttribute("frontendData", data);

        model.addAttribute("isDevMode", "dev".equals(devMode));

        return "index";
    }
}
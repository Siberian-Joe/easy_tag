package com.project.easy_tag.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
//@PreAuthorize("hasAuthority('ADMIN')")
public class AdministrationController {

//    @Autowired
//    UserRepository userRepository;

//    @GetMapping("/admin")
//    public List<User> administration() {
//        return userRepository.findAll();
//    }
}
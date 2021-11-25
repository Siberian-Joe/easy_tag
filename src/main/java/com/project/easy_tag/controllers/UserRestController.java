package com.project.easy_tag.controllers;

import com.project.easy_tag.domains.User;
import com.project.easy_tag.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public User profile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = null;

        if(userDetails != null) {
            user = userService.findByEmail(userDetails.getUsername());
            user.setPassword(null);
        }

        return user;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.save(user);
    }
}

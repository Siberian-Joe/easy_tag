package com.project.easy_tag.controllers;

import com.project.easy_tag.domains.Role;
import com.project.easy_tag.domains.User;
import com.project.easy_tag.repositories.RoleRepository;
import com.project.easy_tag.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/adminpanel")
public class AdministrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/users")
    public List<User> users() {
        return userService.findAll();
    }

    @GetMapping("/roles")
    public List<Role> roles() {
        return roleRepository.findAll();
    }
}

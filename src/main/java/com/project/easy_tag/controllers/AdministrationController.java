package com.project.easy_tag.controllers;

import com.project.easy_tag.domains.Role;
import com.project.easy_tag.domains.User;
import com.project.easy_tag.repositories.RoleRepository;
import com.project.easy_tag.services.RequestService;
import com.project.easy_tag.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/adminpanel")
public class AdministrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RequestService requestService;

    @GetMapping("/requests")
    public List<User> usersWithRequests() {
        return requestService.findAll();
    }

    @GetMapping("/users")
    public List<User> users() {
        return userService.findAll();
    }

    @GetMapping("/roles")
    public List<Role> roles() {
        return roleRepository.findAll();
    }
}

package com.project.easy_tag.controllers;

import com.project.easy_tag.domains.Request;
import com.project.easy_tag.domains.User;
import com.project.easy_tag.repositories.RoleRepository;
import com.project.easy_tag.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public User profile(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails != null ? userService.findByEmail(userDetails.getUsername()) : null;
    }

    @GetMapping("/request/{id}")
    public User request(@PathVariable("id") Request request) {
        return userService.findByRequest(request);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable("id") User userFromDb, @RequestBody User user) {
        BeanUtils.copyProperties(user, userFromDb, "id", "password", "role");
        return userService.update(userFromDb);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public User delete(@PathVariable("id") User user) {
        return userService.delete(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/role/{id}")
    public User updateRole(@PathVariable("id") User userFromDb, @RequestParam String id) {
        return userService.updateRole(userFromDb, id);
    }
}

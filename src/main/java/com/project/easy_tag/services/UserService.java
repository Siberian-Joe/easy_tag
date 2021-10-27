package com.project.easy_tag.services;

import com.project.easy_tag.domains.Role;
import com.project.easy_tag.domains.Roles;
import com.project.easy_tag.domains.User;
import com.project.easy_tag.repositories.RoleRepository;
import com.project.easy_tag.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    private void generateRoles() {
        for (Roles role : Roles.values())
            if(roleRepository.findByRole(role) == null)
                roleRepository.save(new Role(role));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        if(userRepository.findByEmail(user.getEmail()) == null) {
//            user.setRole(Collections.singleton(roleRepository.findByRole(Roles.USER)));
            return userRepository.save(user);
        }
        return null;
    }
}

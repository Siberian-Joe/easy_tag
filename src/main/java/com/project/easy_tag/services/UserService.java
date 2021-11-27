package com.project.easy_tag.services;

import com.project.easy_tag.domains.Role;
import com.project.easy_tag.domains.Roles;
import com.project.easy_tag.domains.User;
import com.project.easy_tag.repositories.RoleRepository;
import com.project.easy_tag.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    private void generateRoles() {
        for (Roles role : Roles.values())
            if(roleRepository.findByRole(role) == null)
                roleRepository.save(new Role(role));
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        user.setPassword(null);
        return user;
    }

    public List<User> findAll() {
        List<User> users = userRepository.findAll();

        for (User user:users)
            user.setPassword(null);
        return users;
    }

    public User save(User user) {
        if(userRepository.findByEmail(user.getEmail()) == null) {
            Role userRole = roleRepository.findByRole(Roles.USER);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRole(new HashSet<>(Arrays.asList(userRole)));
            userRepository.save(user);
            user.setPassword(null);
            return user;
        }
        return null;
    }

    public User update(User user) {
        User newUser = userRepository.save(user);

        newUser.setPassword(null);

        return newUser;
    }

    public User updateRole(User user, Role role) {
        if(role != null) {
            user.setRole(new HashSet<>(Arrays.asList(role)));
            User newUser = userRepository.save(user);
            newUser.setPassword(null);
            return newUser;
        }
        else
            return null;
    }

    public User delete(User user) {
        userRepository.delete(user);
        return userRepository.findByEmail(user.getEmail());
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach(role -> {
            roles.add(new SimpleGrantedAuthority(role.getRole().toString()));
        });
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRole());
            return buildUserForAuthentication(user, authorities);
        }
        else
            throw new UsernameNotFoundException("Email not found");
    }
}

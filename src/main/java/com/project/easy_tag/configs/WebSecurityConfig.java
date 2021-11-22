package com.project.easy_tag.configs;

import com.project.easy_tag.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;

    @Bean
    public UserDetailsService mongoUserDetails() {
        return new UserService();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        UserDetailsService userDetailsService = mongoUserDetails();
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().loginPage("/").and()
                .authorizeRequests().antMatchers("/", "/login**", "/js/**", "/error**", "/img/**", "/logos/**").permitAll().anyRequest().authenticated()
                .and().httpBasic()
                .and().sessionManagement().disable();
    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.antMatcher("/**")
//                .authorizeRequests()
//                .antMatchers("/", "/login**", "/js/**", "/error**").permitAll()
//                .anyRequest().authenticated()
//                .and().logout().logoutSuccessUrl("/").permitAll()
//                .and()
//                .logout().logoutSuccessUrl("/").permitAll()
//                .and()
//                .csrf().disable();
//    }
//
////    @Bean
////    public PrincipalExtractor principalExtractor(UserRepository userRepository) {
////        return map -> {
////            String id = (String) map.get("sub");
////            User user = userRepository.findById(id).orElseGet(() -> {
////                User newUser = new User();
////
////                newUser.setId(id);
////                newUser.setName((String) map.get("name"));
////                newUser.setEmail((String) map.get("email"));
////                newUser.setLocale((String) map.get("locale"));
////                newUser.setUserPicture((String) map.get("picture"));
////                newUser.setRoles(Collections.singleton(Role.ADMIN));
////
////                return newUser;
////            });
////
////            user.setLastVisit(LocalDateTime.now());
////
////            return userRepository.save(user);
////        };
////    }
}

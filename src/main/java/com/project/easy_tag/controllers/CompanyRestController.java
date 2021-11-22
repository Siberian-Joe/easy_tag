package com.project.easy_tag.controllers;

import com.project.easy_tag.domains.Company;
import com.project.easy_tag.services.CompanyService;
import com.project.easy_tag.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company/")
public class CompanyRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public Company company(@RequestParam String name) {
        return companyService.findByName(name);
    }

    @PostMapping
    public Company create(@RequestBody Company company) {
        return companyService.save(company);
    }

    @PutMapping("/items/{id}")
    public Company createItem(@PathVariable("id") Company companyFromDb, @RequestBody Company company) {
        BeanUtils.copyProperties(company, companyFromDb, "id");
        System.out.println(companyFromDb);
        System.out.println(company);
        return companyService.save(companyFromDb);
    }

    @PutMapping("{id}")
    public Company update(@PathVariable("id") Company companyFromDb, @RequestBody Company company) {
        BeanUtils.copyProperties(company, companyFromDb, "id");

        return companyService.save(companyFromDb);
    }

//    @PutMapping("/setcompany/{id}")
//    public Company setCompany(@PathVariable("id") User user) {
//        return companyService.createCompany(user, new Company());
//    }

//    @PostMapping
//    public User create(@RequestBody User user) {
//        return userService.save(user);
//    }

}

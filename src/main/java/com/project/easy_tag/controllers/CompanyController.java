package com.project.easy_tag.controllers;

import com.project.easy_tag.domains.Company;
import com.project.easy_tag.domains.User;
import com.project.easy_tag.services.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company/")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/{id}")
    public Company company(@PathVariable("id") String id) {
        return companyService.findById(id);
    }

    @PostMapping
    public Company save(@RequestBody Company company) {
        return companyService.save(company);
    }

    @PostMapping("/create/{id}")
    public Company create(@PathVariable("id") User user, @RequestBody Company company) {
        return companyService.create(user, company);
    }

    @PutMapping("/{id}")
    public Company update(@PathVariable("id") Company companyFromDb, @RequestBody Company company) {
        BeanUtils.copyProperties(company, companyFromDb, "id");
        return companyService.save(companyFromDb);
    }

    @DeleteMapping("/{id}")
    public void update(@PathVariable("id") User user) {
        companyService.delete(user);
    }
}
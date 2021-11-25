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
    private CompanyService companyService;

    @GetMapping("/{id}")
    public Company company(@PathVariable("id") String id) {
        return companyService.findById(id);
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
}

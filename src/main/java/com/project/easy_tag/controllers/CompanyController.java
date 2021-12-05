package com.project.easy_tag.controllers;

import com.project.easy_tag.domains.Company;
import com.project.easy_tag.domains.User;
import com.project.easy_tag.services.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/company/")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/{id}")
    public Company company(@PathVariable("id") String id) {
        return companyService.findById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create/{id}")
    public Company create(@PathVariable("id") User user, @RequestBody Company company) {
        return companyService.create(user, company);
    }

    @PutMapping("/{id}")
    public Company update(@PathVariable("id") Company companyFromDb, @RequestBody Company company) {
        BeanUtils.copyProperties(company, companyFromDb, "id", "qrCode", "logo");
        return companyService.save(companyFromDb);
    }

    @PostMapping("/logo/{id}")
    public Company updateLogo(@PathVariable("id") Company company, @RequestParam("file") MultipartFile file) {
        return companyService.saveLogo(company, file);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") User user) {
        companyService.delete(user);
    }
}

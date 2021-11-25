package com.project.easy_tag.services;

import com.project.easy_tag.domains.Company;
import com.project.easy_tag.domains.User;
import com.project.easy_tag.repositories.CompanyRepository;
import com.project.easy_tag.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

    public Company findById(String id) {
        return companyRepository.findById(id).orElse(null);
    }

    public Company createCompany(User user, Company company) {
        user.setCompany(companyRepository.save(company));
        userRepository.save(user);
        return company;
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }
}

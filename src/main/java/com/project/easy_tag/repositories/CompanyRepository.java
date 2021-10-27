package com.project.easy_tag.repositories;

import com.project.easy_tag.domains.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {
    Company findByName(String name);
}

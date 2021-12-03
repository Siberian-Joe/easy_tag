package com.project.easy_tag.repositories;

import com.project.easy_tag.domains.Company;
import com.project.easy_tag.domains.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    List<User> findAllByRequestNotNull();
}

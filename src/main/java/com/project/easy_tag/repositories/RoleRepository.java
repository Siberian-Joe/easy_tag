package com.project.easy_tag.repositories;

import com.project.easy_tag.domains.Role;
import com.project.easy_tag.domains.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByRole(Roles role);
}

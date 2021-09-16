package com.project.easy_tag.repositories;

import com.project.easy_tag.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<User, String> {
}

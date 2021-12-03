package com.project.easy_tag.repositories;

import com.project.easy_tag.domains.Request;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends MongoRepository<Request, String> {
}

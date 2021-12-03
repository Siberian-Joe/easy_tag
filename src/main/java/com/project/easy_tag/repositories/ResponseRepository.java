package com.project.easy_tag.repositories;

import com.project.easy_tag.domains.Response;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends MongoRepository<Response, String> {
}

package com.project.easy_tag.repositories;

import com.project.easy_tag.domains.TypeOfResponse;
import com.project.easy_tag.domains.TypesOfResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfResponseRepository extends MongoRepository<TypeOfResponse, String> {
    TypeOfResponse findByType(TypesOfResponse type);
}

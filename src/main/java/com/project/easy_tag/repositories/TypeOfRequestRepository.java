package com.project.easy_tag.repositories;

import com.project.easy_tag.domains.TypeOfRequest;
import com.project.easy_tag.domains.TypesOfRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfRequestRepository extends MongoRepository<TypeOfRequest, String> {
    TypeOfRequest findByType(TypesOfRequest type);
}

package com.project.easy_tag.repositories;

import com.project.easy_tag.domains.QRCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QRCodeRepository extends MongoRepository<QRCode, String> {
}

package com.project.easy_tag.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("requests")
@Data
public class Request {

    @Id
    String id;

    String description;

    @DBRef
    TypeOfRequest type;
}

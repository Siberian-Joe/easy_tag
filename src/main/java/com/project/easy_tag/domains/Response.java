package com.project.easy_tag.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("responses")
@Data
public class Response {

    @Id
    private String id;

    private String description;
    private TypeOfResponse type;
}

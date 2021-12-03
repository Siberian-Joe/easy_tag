package com.project.easy_tag.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("types_of_response")
@Data
public class TypeOfResponse {

    @Id
    private String id;

    private TypesOfResponse type;

    public TypeOfResponse(TypesOfResponse type) {
        this.type = type;
    }
}

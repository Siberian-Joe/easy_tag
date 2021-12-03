package com.project.easy_tag.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("types_of_request")
@Data
public class TypeOfRequest {

    @Id
    private String id;

    private TypesOfRequest type;

    public TypeOfRequest(TypesOfRequest type) {
        this.type = type;
    }
}

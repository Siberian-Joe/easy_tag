package com.project.easy_tag.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("roles")
@Data
public class Role {

    @Id
    private String id;

    private String role;
//    private Roles role;
//
//    public Role(Roles role) {
//        this.role = role;
//    }
}
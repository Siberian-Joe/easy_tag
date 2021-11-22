package com.project.easy_tag.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document("users")
@Data
public class User {
    @Id
    private String id;

    private String fullName;
    private String email;
    private String password;

    @DBRef
    private Company company;

    @DBRef
    private Set<Role> role;
}

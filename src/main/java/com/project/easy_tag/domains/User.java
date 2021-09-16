package com.project.easy_tag.domains;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {
    @Id
    private String id;
    private String name;
    private String userPicture;
    private String email;
    private String locale;
    private LocalDateTime lastVisit;
}

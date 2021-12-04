package com.project.easy_tag.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document("companies")
@Data
public class Company {
    @Id
    private String id;

    private String name;

    private Set<Item> items;

    @DBRef
    private QRCode qrCode;
}
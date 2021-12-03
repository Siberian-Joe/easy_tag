package com.project.easy_tag.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("qr_codes")
@Data
public class QRCode {
    @Id
    private String id;

    private String name;

    public QRCode(String name) {
        this.name = name;
    }
}

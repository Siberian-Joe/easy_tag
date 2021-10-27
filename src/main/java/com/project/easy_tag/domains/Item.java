package com.project.easy_tag.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Item {
    @Id
    private String id;

    private String name;
    private String icon;
    private String href;
}

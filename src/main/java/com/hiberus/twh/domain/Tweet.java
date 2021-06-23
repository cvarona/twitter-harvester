package com.hiberus.twh.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor(force = true)
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Document
public class Tweet {

    @Id
    private Long id;

    private String user;
    private String text;
    private String location;

    private List<String> hashtags;

    private boolean validated;
}

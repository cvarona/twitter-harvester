package com.hiberus.twh.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TweetDto {

    private Long id;

    private String user;
    private String text;
    private String location;

    private List<String> hashtags;

    private boolean validated;
}

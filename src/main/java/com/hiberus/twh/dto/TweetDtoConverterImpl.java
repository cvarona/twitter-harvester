package com.hiberus.twh.dto;

import org.springframework.stereotype.Component;

@Component
public class TweetDtoConverterImpl implements TweetDtoConverter {
    public TweetDto toDto(com.hiberus.twh.domain.Tweet t) {
        return new TweetDto(t.getId(), t.getUser(), t.getText(), t.getLocation(), t.getHashtags(), t.isValidated());
    }
}

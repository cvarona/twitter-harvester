package com.hiberus.twh.dto;

@FunctionalInterface
public interface TweetDtoConverter {
    TweetDto toDto(com.hiberus.twh.domain.Tweet t);
}

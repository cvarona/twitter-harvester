package com.hiberus.twh.service;

import com.hiberus.twh.dto.TweetDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface TweetQueryService {
    Flux<TweetDto> findAll();

    Flux<TweetDto> findValidated();

    Mono<Set<String>> getMostUsedHashtags();

    Mono<Boolean> validate(Long tweetId);
}

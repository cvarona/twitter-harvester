package com.hiberus.twh.repository;

import com.hiberus.twh.domain.Tweet;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TweetRepository extends ReactiveMongoRepository<Tweet, Long> {

    @Query("{ 'validated': true }")
    Flux<Tweet> findValidated();
}

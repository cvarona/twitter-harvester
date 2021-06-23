package com.hiberus.twh;

import com.github.javafaker.Faker;
import com.hiberus.twh.domain.Tweet;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TweetMocks {

    public static final int INITIAL_REPO_TWEET_COUNT = 5;
    public static final List<Tweet> TWEETS = TweetMocks.createTweetMocks();

    private static List<Tweet> createTweetMocks() {
        Faker faker = new Faker();
        return IntStream.range(0, INITIAL_REPO_TWEET_COUNT).mapToObj(t -> {
            Tweet tweet = new Tweet();
            tweet.setId(faker.number().randomNumber());
            tweet.setText(faker.lebowski().quote());
            tweet.setUser(faker.internet().emailAddress());
            tweet.setHashtags(IntStream.range(0, faker.number().numberBetween(1, 3)).mapToObj(i -> faker.ancient().god()).collect(Collectors.toList()));
            return tweet;
        }).collect(Collectors.toList());
    }
}

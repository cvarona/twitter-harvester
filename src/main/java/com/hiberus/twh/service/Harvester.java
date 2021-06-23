package com.hiberus.twh.service;

import com.hiberus.twh.domain.Tweet;
import com.hiberus.twh.repository.TweetRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import twitter4j.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
@Scope("singleton")
public class Harvester implements StatusListener {

    @Value("${twh.defaultMinimumFollowerCount}")
    private int minimumFollowerCount;

    @Value("${twh.defaultTargetLanguages}")
    private String targetLanguagesStr;

    @Resource
    private TweetRepository tweetRepository;

    private Set<String> targetLanguages;

    private long insertCount = 0;

    @PostConstruct
    public void initTargetLanguages() {
        targetLanguages = new HashSet<String>(Arrays.asList(this.targetLanguagesStr.split(",")));
    }

    @Override
    public void onStatus(Status status) {

        HashtagEntity[] hashtagEntities = status.getHashtagEntities();
        if (hashtagEntities.length != 0 && status.getUser().getFollowersCount() >= minimumFollowerCount && targetLanguages.contains(status.getLang().toLowerCase())) {

            log.info("I'll keep {}", status.getText());
            Tweet tweet = new Tweet();
            tweet.setId(status.getId());
            tweet.setUser(status.getUser().getName());
            tweet.setText(status.getText());

            GeoLocation geoLocation = status.getGeoLocation();
            if (geoLocation != null) {
                String location = String.format("%s lat, %s lon", geoLocation.getLatitude(), geoLocation.getLongitude());
                tweet.setLocation(location);
            }

            List<String> hashtags = Arrays.stream(hashtagEntities).map(hte -> hte.getText()).collect(Collectors.toList());
            tweet.setHashtags(hashtags);
            tweet.setValidated(false);

            tweetRepository.save(tweet).subscribe().dispose();

            log.info("Tweet count: {}", ++insertCount);
        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
    }

    @Override
    public void onStallWarning(StallWarning warning) {
    }

    @Override
    public void onException(Exception ex) {
        ex.printStackTrace();
    }
}

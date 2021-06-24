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

    @Resource
    private HarvestFilter filter;

    @Resource
    private TweetRepository tweetRepository;

    private long insertCount = 0;

    @Override
    public void onStatus(Status status) {
        if (filter.harvest(status)) {

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

            List<String> hashtags = Arrays.stream(status.getHashtagEntities()).map(hte -> hte.getText()).collect(Collectors.toList());
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

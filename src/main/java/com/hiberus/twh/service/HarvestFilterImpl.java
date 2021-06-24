package com.hiberus.twh.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twitter4j.HashtagEntity;
import twitter4j.Status;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class HarvestFilterImpl implements HarvestFilter {

    @Value("${twh.defaultMinimumFollowerCount}")
    private int minimumFollowerCount;

    @Value("${twh.defaultTargetLanguages}")
    private String targetLanguagesStr;

    private Set<String> targetLanguages;

    @PostConstruct
    public void initTargetLanguages() {
        // The target languages configuration property is a comma separated string that must be split
        targetLanguages = new HashSet<String>(Arrays.asList(this.targetLanguagesStr.split(",")));
    }

    @Override
    public boolean harvest(Status status) {
        HashtagEntity[] hashtagEntities = status.getHashtagEntities();
        // In order to be able to test the top hashtags retrieval endpoint just tweets with
        // at least one tag will be stored
        return hashtagEntities.length != 0 &&
                status.getUser().getFollowersCount() >= minimumFollowerCount &&
                targetLanguages.contains(status.getLang().toLowerCase());
    }
}

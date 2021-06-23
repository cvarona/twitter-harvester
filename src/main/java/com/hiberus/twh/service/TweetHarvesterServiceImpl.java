package com.hiberus.twh.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import twitter4j.TwitterStreamFactory;

import javax.annotation.Resource;

@Component
@Log4j2
public class TweetHarvesterServiceImpl implements TweetHarvesterService {

    @Resource
    private Harvester harvester;

    @Override
    public void startHarvesting() {
        log.info("Start harvesting");
        new TwitterStreamFactory().getInstance().addListener(harvester).sample();
    }
}

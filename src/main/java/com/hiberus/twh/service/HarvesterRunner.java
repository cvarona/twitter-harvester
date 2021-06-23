package com.hiberus.twh.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Profile("!test")
@Component
public class HarvesterRunner implements ApplicationRunner {

    @Resource
    private TweetHarvesterService tweetHarvesterService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        tweetHarvesterService.startHarvesting();
    }
}

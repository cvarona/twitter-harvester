package com.hiberus.twh.controller;

import com.hiberus.twh.dto.TweetDto;
import com.hiberus.twh.service.TweetQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Set;

@RestController
public class TweetApiController {

    @Resource
    private TweetQueryService tweetQueryService;

    @RequestMapping(path = "/top-hashtags", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<Set<String>> getHashtags() {
        return tweetQueryService.getMostUsedHashtags();
    }

    @RequestMapping(path = "/tweets", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TweetDto> getTweets(@RequestParam(value = "validated", defaultValue = "false") String validated) {
        return Boolean.parseBoolean(validated) ? tweetQueryService.findValidated() : tweetQueryService.findAll();
    }

    @RequestMapping(value = "/tweets/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Boolean> validateTweet(@PathVariable("id") Long id) {
        return tweetQueryService.validate(id);
    }
}

package com.hiberus.twh.service;

import com.hiberus.twh.dto.TweetDto;
import com.hiberus.twh.dto.TweetDtoConverter;
import com.hiberus.twh.repository.TweetRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class TweetQueryServiceImpl implements TweetQueryService {

    @Value("${twh.defaultHashtagCount}")
    private int hashtagCount;

    @Resource
    private TweetRepository tweetRepository;

    @Resource
    private TweetDtoConverter tweetDtoConverter;

    @Override
    public Flux<TweetDto> findAll() {
        log.info("Find all");
        return tweetRepository.findAll().map(tweetDtoConverter::toDto);
    }

    @Override
    public Flux<TweetDto> findValidated() {
        log.info("Find validated");
        return tweetRepository.findValidated().map(tweetDtoConverter::toDto);
    }

    @Override
    public Mono<Set<String>> getMostUsedHashtags() {
        log.info("Get most used hashtags");
        return tweetRepository.findAll().map(t -> t.getHashtags())
                // First we count how many times has appeared every tag so far
                .reduce(new HashMap<String, Integer>(), (mostUsed, next) -> {
                    next.forEach(ht -> {
                        if (mostUsed.containsKey(ht)) {
                            mostUsed.put(ht, mostUsed.get(ht) + 1);
                        } else {
                            mostUsed.put(ht, 1);
                        }
                    });

                    return mostUsed;
                })
                .map(m -> {
                    // We then take the map's entries and sort them according to their value
                    List<Map.Entry<String, Integer>> entries = new ArrayList<>(m.entrySet());
                    entries.sort(Comparator.comparingInt(Map.Entry::getValue));

                    // We then take at most the list last n positions, according to the value
                    // configured in application.properties
                    List<Map.Entry<String, Integer>> mostUsed = entries.size() > hashtagCount ? entries.subList(entries.size() - hashtagCount, entries.size()) : entries;
                    return mostUsed.stream().map(e -> e.getKey()).collect(Collectors.toSet());
                });
    }

    @Override
    public Mono<Boolean> validate(Long tweetId) {
        return tweetRepository.findById(tweetId).flatMap((com.hiberus.twh.domain.Tweet t) -> {
            t.setValidated(true);
            return tweetRepository.save(t).map(st -> Boolean.TRUE);
        }).defaultIfEmpty(Boolean.FALSE);
    }
}

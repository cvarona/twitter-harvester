package com.hiberus.twh.service;

import com.hiberus.twh.TweetMocks;
import com.hiberus.twh.domain.Tweet;
import com.hiberus.twh.dto.TweetDto;
import com.hiberus.twh.repository.TweetRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import javax.annotation.Resource;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles(profiles = "test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TweetQueryServiceTest {

    @Resource
    private TweetRepository tweetRepository;

    @Resource
    private TweetQueryService service;

    @BeforeAll
    public void setUp() {
        tweetRepository.deleteAll();
        TweetMocks.TWEETS.forEach(t -> tweetRepository.save(t).subscribe().dispose());
    }

    @Test
    public void testFindAll() {
        List<Tweet> repoTweets = tweetRepository.findAll().collectList().block();
        List<TweetDto> serviceTweets = service.findAll().collectList().block();
        serviceTweets.forEach(st -> {
            Tweet sourceTweet = repoTweets.stream().filter(rt -> rt.getId().equals(st.getId())).findAny().orElse(null);
            assertThat(sourceTweet).isNotNull();
            assertThat(sourceTweet.getUser()).isEqualTo(st.getUser());
            assertThat(sourceTweet.getText()).isEqualTo(st.getText());
            assertThat(sourceTweet.getHashtags()).isEqualTo(st.getHashtags());
        });
    }

    @Test
    public void testValidation() {
        TweetDto first = service.findAll().collectList().block().get(0);
        service.validate(first.getId()).block();
        service.findValidated().as(StepVerifier::create).assertNext(t -> {
            assertThat(t.isValidated()).isTrue();
            assertThat(t.getId()).isEqualTo(first.getId());
        }).verifyComplete();
    }
}

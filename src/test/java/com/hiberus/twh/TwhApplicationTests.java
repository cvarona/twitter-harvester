package com.hiberus.twh;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
class TwhApplicationTests {

	@Test
	void contextLoads() {
	}
}

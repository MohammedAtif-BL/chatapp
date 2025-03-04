package com.learning.chatapp.chat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class ChatApplicationTests {

	@Test
	void testMain() {
		assertDoesNotThrow(() -> ChatApplication.main(new String[]{}));
	}

}

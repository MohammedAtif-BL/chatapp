package com.learning.chatapp.chat.controller;


import com.learning.chatapp.chat.chat.ChatController;
import com.learning.chatapp.chat.chat.ChatMessage;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.learning.chatapp.chat.chat.MessageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import java.util.HashMap;

class ChatControllerTest {

    @InjectMocks
    private ChatController chatController;

    @Mock
    private SimpMessageHeaderAccessor headerAccessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Positive Test: Sending a valid message
    @Test
    void testSendMessage_Success() {
        ChatMessage chatMessage = new ChatMessage(MessageType.CHAT, "Hello", "User1");

        ChatMessage response = chatController.sendMessage(chatMessage);

        assertNotNull(response);
        assertEquals(MessageType.CHAT, response.getType());
        assertEquals("Hello", response.getContent());
        assertEquals("User1", response.getSender());
    }

    // Negative Test: Sending a null message
    @Test
    void testSendMessage_NullMessage() {
        ChatMessage response = chatController.sendMessage(null);

        assertNull(response, "Response should be null for null input");
    }

    // Positive Test: Adding a user successfully
    @Test
    void testAddUser_Success() {
        ChatMessage chatMessage = new ChatMessage(MessageType.JOIN, "Hello!", "JohnDoe");

        when(headerAccessor.getSessionAttributes()).thenReturn(new HashMap<>());

        ChatMessage response = chatController.addUser(chatMessage, headerAccessor);

        assertNotNull(response);
        assertEquals("JohnDoe", response.getSender());
        assertTrue(headerAccessor.getSessionAttributes().containsKey("username"));
        assertEquals("JohnDoe", headerAccessor.getSessionAttributes().get("username"));
    }

    @Test
    void testAddUser_NullSender() {
        ChatMessage chatMessage = new ChatMessage(MessageType.JOIN, "Welcome!", null);

        when(headerAccessor.getSessionAttributes()).thenReturn(new HashMap<>());

        ChatMessage response = chatController.addUser(chatMessage, headerAccessor);

        assertNotNull(response);
        assertNull(response.getSender());
        assertFalse(headerAccessor.getSessionAttributes().containsKey("username"), "Username should not be added");
    }

    @Test
    void testAddUser_NullSessionAttributes() {
        ChatMessage chatMessage = new ChatMessage(MessageType.JOIN, "Hello!", "JohnDoe");

        headerAccessor = SimpMessageHeaderAccessor.create();
        // ✅ Do not set session attributes to simulate null case

        ChatMessage response = chatController.addUser(chatMessage, headerAccessor);

        assertNotNull(response);
        assertEquals("JohnDoe", response.getSender());
        assertNotNull(headerAccessor.getSessionAttributes(), "Session attributes should be initialized");
        assertTrue(headerAccessor.getSessionAttributes().containsKey("username"));
    }


}

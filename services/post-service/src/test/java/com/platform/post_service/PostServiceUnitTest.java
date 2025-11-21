package com.platform.post_service;

import com.platform.post_service.dto.PostEvent;
import com.platform.post_service.dto.PostRequest;
import com.platform.post_service.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class PostServiceUnitTest {

    @Mock
    KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    PostService postService;

    @Test
    void createPost_sendsKafka_and_storesSnapshot() {
        PostEvent ev = postService.createPost(new PostRequest("user-1", "hello unit"));
        assertNotNull(ev.getPostId());
        // verify that kafkaTemplate.send was called once with topic "posts"
        verify(kafkaTemplate, times(1)).send(eq("posts"), eq(ev.getPostId()), any(PostEvent.class));
        assertTrue(postService.getPost(ev.getPostId()).isPresent());
    }
}

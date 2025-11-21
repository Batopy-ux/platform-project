package com.platform.post_service.service;

import com.platform.post_service.dto.PostEvent;
import com.platform.post_service.dto.PostRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PostService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final Map<String, PostEvent> store = new ConcurrentHashMap<>();


    public PostService(KafkaTemplate<String, Object> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public PostEvent createPost(PostRequest postRequest){
        String postId = UUID.randomUUID().toString();
        String authorId = postRequest.getAuthorId();
        String content = postRequest.getContent();
        long createdAt = System.currentTimeMillis();

        PostEvent postEvent = new PostEvent(postId, authorId, content, createdAt);
        store.put(postId, postEvent);
        kafkaTemplate.send("posts", postEvent);
        return postEvent;
    }

    public Optional<PostEvent> getPost(String postId){
        return Optional.ofNullable(store.get(postId));
    }
}

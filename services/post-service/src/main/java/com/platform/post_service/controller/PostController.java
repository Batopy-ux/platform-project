package com.platform.post_service.controller;


import com.platform.post_service.dto.PostEvent;
import com.platform.post_service.dto.PostRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.platform.post_service.service.PostService;

@Tag(name = "Post Controller", description = "Create post and read")
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostEvent> createPost(@RequestBody PostRequest postRequest){
        PostEvent postEvent = postService.createPost(postRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(postEvent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostEvent> getPost(@PathVariable String id){
        return postService.getPost(id)
                .map(postEvent -> ResponseEntity.ok(postEvent))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

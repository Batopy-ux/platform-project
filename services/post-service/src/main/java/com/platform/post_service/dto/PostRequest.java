package com.platform.post_service.dto;

import jakarta.validation.constraints.NotBlank;

public class PostRequest {

    @NotBlank
    private String authorId;

    @NotBlank
    private String content;

    public PostRequest(String authorId, String content){
        this.authorId = authorId;
        this.content = content;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

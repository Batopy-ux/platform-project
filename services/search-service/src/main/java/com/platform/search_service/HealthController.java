package com.platform.search_service;

@RestController
public class HealthController{

    @GetMapping("/health")
    public String healthCheck(){
        return "Post service is up and running";
    }

}
package com.example.asyncexample.api;

import com.example.asyncexample.dto.PostDto;
import com.example.asyncexample.dto.UserDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class JSONPlaceholderAPI {

    public Mono<UserDto> getUser(Integer id) {
        return null;
    }

    public Flux<PostDto> getPosts(Integer userId) {
        return null;
    }
}

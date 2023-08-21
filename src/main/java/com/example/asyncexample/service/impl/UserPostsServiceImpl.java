package com.example.asyncexample.service.impl;

import com.example.asyncexample.dto.UserPostsDto;
import com.example.asyncexample.service.UserPostsService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserPostsServiceImpl implements UserPostsService {
    @Override
    public Mono<UserPostsDto> getUserAndPosts(Integer id) {
    return null;
    }
}

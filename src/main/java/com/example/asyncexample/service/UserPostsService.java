package com.example.asyncexample.service;

import com.example.asyncexample.dto.UserPostsDto;
import reactor.core.publisher.Mono;

public interface UserPostsService {

  Mono<UserPostsDto> getUserAndPosts(Integer id);
}

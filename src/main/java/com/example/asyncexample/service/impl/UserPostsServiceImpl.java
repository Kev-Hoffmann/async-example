package com.example.asyncexample.service.impl;

import com.example.asyncexample.api.JSONPlaceholderAPI;
import com.example.asyncexample.dto.UserPostsDto;
import com.example.asyncexample.service.UserPostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserPostsServiceImpl implements UserPostsService {

  private final JSONPlaceholderAPI jsonPlaceholderAPI;

  @Override
  public Mono<UserPostsDto> getUserAndPosts(Integer id) {
    return Mono.zip(jsonPlaceholderAPI.getUser(id), jsonPlaceholderAPI.getPosts(id).collectList())
        .map(userAndPosts -> new UserPostsDto(userAndPosts.getT1(), userAndPosts.getT2()));
  }
}

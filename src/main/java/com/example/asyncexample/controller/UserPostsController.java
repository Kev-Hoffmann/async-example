package com.example.asyncexample.controller;

import com.example.asyncexample.dto.UserPostsDto;
import com.example.asyncexample.service.UserPostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserPostsController {

  private final UserPostsService userPostsService;

  @GetMapping("/users/{id}/posts")
  public Mono<UserPostsDto> getUserAndPosts(@PathVariable Integer id) {
    log.info("Request to get user and posts for id: " + id);
    return userPostsService.getUserAndPosts(id);
  }
}

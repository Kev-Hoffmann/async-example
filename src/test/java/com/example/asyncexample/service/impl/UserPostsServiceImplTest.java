package com.example.asyncexample.service.impl;

import com.example.asyncexample.api.JSONPlaceholderAPI;
import com.example.asyncexample.dto.PostDto;
import com.example.asyncexample.dto.UserDto;
import com.example.asyncexample.dto.UserPostsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserPostsServiceImplTest {

  @Mock private JSONPlaceholderAPI jsonPlaceholderApi;

  @InjectMocks private UserPostsServiceImpl userPostsService;

  @Test
  public void shouldReturnUserPostsDto() {
    int userId = 1;

    UserDto expectedUserDto = new UserDto();
    expectedUserDto.setId(userId);
    PostDto expectedPostDto1 = new PostDto();
    expectedPostDto1.setUserId(userId);
    PostDto expectedPostDto2 = new PostDto();
    expectedPostDto2.setUserId(userId);


    when(jsonPlaceholderApi.getUser(userId)).thenReturn(Mono.just(expectedUserDto));
    when(jsonPlaceholderApi.getPosts(userId))
        .thenReturn(Flux.just(expectedPostDto1, expectedPostDto2));

    UserPostsDto userPosts = userPostsService.getUserAndPosts(userId).block();

    assertNotNull(userPosts);
    assertEquals(expectedUserDto, userPosts.getUser());
    assertEquals(2, userPosts.getPosts().size());
    assertTrue(userPosts.getPosts().contains(expectedPostDto1));
    assertTrue(userPosts.getPosts().contains(expectedPostDto2));
  }
}

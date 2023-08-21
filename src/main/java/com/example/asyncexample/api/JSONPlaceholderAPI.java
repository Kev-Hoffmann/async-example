package com.example.asyncexample.api;

import com.example.asyncexample.dto.PostDto;
import com.example.asyncexample.dto.UserDto;
import com.example.asyncexample.error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
@RequiredArgsConstructor
public class JSONPlaceholderAPI {

  private final WebClient webClient;

  public Mono<UserDto> getUser(Integer id) {
    return webClient
        .get()
        .uri("/users/" + id)
        .retrieve()
        .onStatus(
            NOT_FOUND::equals,
            clientResponse ->
                Mono.error(new ResourceNotFoundException("User " + id + " not found!")))
        .bodyToMono(UserDto.class);
  }

  public Flux<PostDto> getPosts(Integer userId) {
    return webClient.get().uri("/posts?userId=" + userId).retrieve().bodyToFlux(PostDto.class);
  }
}

package com.example.asyncexample.api;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.OK;

import com.example.asyncexample.dto.PostDto;
import com.example.asyncexample.dto.UserDto;
import com.example.asyncexample.error.ResourceNotFoundException;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
@WireMockTest(httpPort = 9090)
public class JSONPlaceholderAPITest {

  private final JSONPlaceholderAPI jsonPlaceholderAPI =
      new JSONPlaceholderAPI(WebClient.create("http://localhost:9090"));

  @Test
  public void shouldReturnUser() {
    int userId = 1;

    stubFor(
        get(urlEqualTo("/users/" + userId))
            .willReturn(
                aResponse()
                    .withStatus(OK.value())
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("user.json")));

    UserDto userDto = jsonPlaceholderAPI.getUser(userId).block();

    assertNotNull(userDto);
  }

  @Test
  public void shouldReturnResourceNotFoundException() {
    int userId = 123;

    stubFor(get(urlEqualTo("/users/" + userId)).willReturn(notFound()));

    ResourceNotFoundException resourceNotFoundException =
        assertThrows(
            ResourceNotFoundException.class, () -> jsonPlaceholderAPI.getUser(userId).block());

    assertEquals("User " + userId + " not found!", resourceNotFoundException.getLocalizedMessage());
  }

  @Test
  public void shouldReturnPosts() {
    int userId = 1;

    stubFor(
        get(urlEqualTo("/posts?userId=" + userId))
            .willReturn(
                aResponse()
                    .withStatus(OK.value())
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("posts.json")));

    List<PostDto> postDtoList = jsonPlaceholderAPI.getPosts(userId).collectList().block();

    assertNotNull(postDtoList);
    assertEquals(2, postDtoList.size());
  }
}

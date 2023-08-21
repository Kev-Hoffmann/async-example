package com.example.asyncexample.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.OK;

import com.example.asyncexample.dto.UserPostsDto;
import com.example.asyncexample.error.dto.ErrorMessageDto;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@WireMockTest(httpPort = 9090)
public class UserPostsControllerTest {

  @Autowired private WebTestClient webTestClient;

  @Test
  public void shouldSuccessfullyGetUserAndPosts() {
    int userId = 1;

    stubFor(
        get(urlEqualTo("/users/" + userId))
            .willReturn(
                aResponse()
                    .withStatus(OK.value())
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("user.json")));
    stubFor(
        get(urlEqualTo("/posts?userId=" + userId))
            .willReturn(
                aResponse()
                    .withStatus(OK.value())
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("posts.json")));

    UserPostsDto responseBody =
        webTestClient
            .get()
            .uri("/api/v1/users/" + userId + "/posts")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(UserPostsDto.class)
            .returnResult()
            .getResponseBody();

    assertNotNull(responseBody);

    assertEquals(userId, responseBody.getUser().getId());
  }

  @Test
  public void shouldReturnNotFoundAndErrorMessageIfUserIsNotFound() {
    int userId = 123;

    stubFor(get(urlEqualTo("/users/" + userId)).willReturn(notFound()));
    stubFor(get(urlEqualTo("/posts?userId=" + userId)).willReturn(ok()));

    ErrorMessageDto responseBody =
        webTestClient
            .get()
            .uri("/api/v1/users/" + userId + "/posts")
            .exchange()
            .expectStatus()
            .isNotFound()
            .expectBody(ErrorMessageDto.class)
            .returnResult()
            .getResponseBody();

    assertNotNull(responseBody);
    assertEquals("Resource Not Found", responseBody.getTitle());
    assertEquals(HttpStatus.NOT_FOUND.value(), responseBody.getCode());
    assertEquals("User " + userId + " not found!", responseBody.getDetails());
  }
}

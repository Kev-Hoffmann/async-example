package com.example.asyncexample.controller;

import com.example.asyncexample.dto.UserPostsDto;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.OK;

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
}

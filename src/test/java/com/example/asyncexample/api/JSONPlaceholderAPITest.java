package com.example.asyncexample.api;

import com.example.asyncexample.dto.PostDto;
import com.example.asyncexample.dto.UserDto;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
@WireMockTest(httpPort = 9090)
public class JSONPlaceholderAPITest {

    @InjectMocks
    private JSONPlaceholderAPI jsonPlaceholderAPI;

    @Test
    public void shouldReturnUser() {
        int userId = 1;

        stubFor(get(urlEqualTo("/users/" + userId)).willReturn( aResponse()
                .withStatus(OK.value())
                .withHeader("Content-Type", "application/json")
                .withBodyFile("user.json")));

        UserDto userDto = jsonPlaceholderAPI.getUser(userId).block();

        assertNotNull(userDto);
    }

    @Test
    public void shouldReturnPosts() {
        int userId = 1;

        stubFor(get(urlEqualTo("/posts?userId=" + userId)).willReturn( aResponse()
                .withStatus(OK.value())
                .withHeader("Content-Type", "application/json")
                .withBodyFile("posts.json")));

        List<PostDto> postDtoList = jsonPlaceholderAPI.getPosts(userId).collectList().block();

        assertNotNull(postDtoList);
    }
}

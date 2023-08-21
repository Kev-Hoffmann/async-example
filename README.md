# Challenge
Create an application that fetches data asynchronously from two endpoints 
and displays the merged response as JSON response from an REST API.

Example endpoints:

http://jsonplaceholder.typicode.com/users/1

http://jsonplaceholder.typicode.com/posts?userId=1

# Solution

The solution for this challenge is to create a Spring Boot application using Spring Webflux to handle the asynchronous requests.

### Implementation

- Java 17
- Maven
- Spring Boot 3
- Spring Webflux

### Testing

- Mockito
- WireMock

### Extra Plugins

- JaCoCo -> ensure test coverage
- fmt-maven-plugin -> ensure code formatting
- springdoc -> generate Open API Spec

package com.example.kata316;

import com.example.kata316.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Kata316Application {

    private static RestTemplate restTemplate = new RestTemplate();
    private static StringBuilder resultCode = new StringBuilder();
    private static String url = "http://94.198.50.185:7081/api/users";
    private static HttpHeaders headers;
    private static HttpEntity<User> requestEntity;
    private static String sessionId;
    private static User[] users;

    public static void main(String[] args) {
        SpringApplication.run(Kata316Application.class, args);
        init();
        getSessionIdAndUsers();
        User user = new User(3L, "James", "Brown", (byte) 25);
        resultCode.append(saveNewUser(user));
        System.out.println("КОД: " + resultCode);

        resultCode.append(updateUser(user));
        System.out.println("КОД: " + resultCode);

        resultCode.append(deleteUser(3L));
        System.out.println("КОД: " + resultCode);
    }

    private static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // требуется только для реквестов из браузера
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
    }

    private static void getSessionIdAndUsers() {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(url, User[].class);

        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println("status code - " + statusCode);

        users = responseEntity.getBody();
        System.out.println("response body - " + Arrays.toString(users));

        HttpHeaders responseHeaders = responseEntity.getHeaders();
        System.out.println("response headers -" + responseHeaders);

        List<String> cookies = responseHeaders.get(HttpHeaders.SET_COOKIE);
        sessionId = cookies.get(0).substring(0, "JSESSIONID=".length() + 32);
        System.out.println(sessionId);
        headers.add(HttpHeaders.COOKIE, sessionId);
    }

    private static String saveNewUser(User user) {
        requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        return responseEntity.getBody();
    }

    private static String updateUser(User user) {
        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        return responseEntity.getBody();
    }

    private static String deleteUser(Long id) {
        HttpEntity<User> request = new HttpEntity<>(new User(), headers);
        ResponseEntity<String> response = restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, request, String.class);
        return response.getBody();
    }

}

package com.broker.controller;

import com.broker.rabbitmq.ConfigureRabbitMQ;
import io.swagger.v3.oas.annotations.Operation;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.Arrays;

@RestController
@RequestMapping("/greencare")
public class BrokerServiceRestController {

    @Autowired
    private RestTemplate restTemplate;
    //private String accountURLforPC = "http://localhost:8082/account-service";
    private String accountURL = "http://account:8082/account-service";
    //private String searchURL = "http://localhost:8081/search-service";


    @Operation(summary = "This method takes the username of a user, and retrieves the email attached to that user" +
            ", assuming the user exists in the database.")
    @GetMapping("/account-service/get-user")
    public ResponseEntity<String> getUser(@RequestBody String jsonString) {
        String url = accountURL + "/get-user";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return checkForError(response.getBody());
    }

    @Operation(summary = "This method creates a user, taking a username, email, and password")
    @PostMapping("/account-service/create-user")
    public ResponseEntity<String> createUser(@RequestBody String jsonString) {
        String url = accountURL + "/create-user";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return checkForError(response.getBody());
    }


    @Operation(summary = "This method takes the username and password of a user, and returns whether the password" +
            "is correct.")
    @GetMapping("/account-service/authenticate-user")
    public ResponseEntity<String> authUser(@RequestBody String jsonString) {
        String url = accountURL + "/authenticate-user";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return checkForError(response.getBody());
    }

    @Operation(summary = "This method takes a username and password, and deletes the user from the database" +
            "of the username and password are correct.")
    @DeleteMapping("/account-service/delete-user")
    public ResponseEntity<String> deleteUser(@RequestBody String jsonString) {
        String url = accountURL + "/delete-user";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return checkForError(response.getBody());
    }

    public ResponseEntity<String> checkForError(String jsonString){
        JSONObject json = new JSONObject(jsonString);
        Boolean isError = json.getBoolean("error");
        if(isError){    //Is Error
            return ResponseEntity.status(HttpStatus.resolve(json.getInt("status"))).body(jsonString);
        }else{          //Is not Error
            return ResponseEntity.status(HttpStatus.OK).body(jsonString);
        }
    }

}
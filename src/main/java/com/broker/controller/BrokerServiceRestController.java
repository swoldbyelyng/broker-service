package com.broker.controller;

import com.broker.rabbitmq.ConfigureRabbitMQ;
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
    private String accountURL = "http://localhost:8082/account-service";
    private String searchURL = "http://localhost:8081/search-service";


    @GetMapping("/account-service/get-user")
    public ResponseEntity<String> getUser(@RequestBody String jsonString) {
        String url = accountURL + "/get-user";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return checkForError(response.getBody());
    }

    @PostMapping("/account-service/create-user")
    public ResponseEntity<String> createUser(@RequestBody String jsonString) {
        String url = accountURL + "/create-user";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return checkForError(response.getBody());
    }


    @GetMapping("/account-service/authenticate-user")
    public ResponseEntity<String> authUser(@RequestBody String jsonString) {
        String url = accountURL + "/authenticate-user";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return checkForError(response.getBody());
    }

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
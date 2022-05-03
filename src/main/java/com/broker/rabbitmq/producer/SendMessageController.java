package com.broker.rabbitmq.producer;

import com.broker.rabbitmq.ConfigureRabbitMQ;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessageController {

    private final RabbitTemplate rabbitTemplate;

    public SendMessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message){
        rabbitTemplate.convertAndSend(ConfigureRabbitMQ.EXCHANGE_NAME,
                "logger.message", message);
        System.out.println("message out: " + message);
        return "We have sent a message : " + message;
    }
}
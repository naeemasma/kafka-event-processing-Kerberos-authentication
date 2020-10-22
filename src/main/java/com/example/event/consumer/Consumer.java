package com.example.event.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;
import com.example.domain.EventMessage;

@Service
public class Consumer{
	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
	
    @KafkaListener(topics = "${app.consumer.topic.subscribed-to}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(EventMessage message,
            @Headers MessageHeaders headers) {
    	logger.info("Consumed message: " + message.getDescription());
    }
}

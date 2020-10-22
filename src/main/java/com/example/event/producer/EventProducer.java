package com.example.event.producer;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.example.domain.EventMessage;
@Service	
public class EventProducer {
	@Autowired
    private KafkaTemplate<Long, EventMessage> kafkaTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(EventProducer.class);
	
	public void sendEventMessage(String topic, String evtMsg){
		EventMessage msg = new EventMessage(new Long((new Random()).nextInt(9)),evtMsg);
        Message<EventMessage> message = MessageBuilder
                .withPayload(msg)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();   
    	logger.info("Producing message: " + msg.getDescription());
        kafkaTemplate.send(message);
    }
}
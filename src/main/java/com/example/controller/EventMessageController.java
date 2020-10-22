package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.event.producer.EventProducer;

@RestController
@RequestMapping(value = "/kafka")
public class EventMessageController {
	@Value("${app.consumer.topic.subscribed-to}")
	private String topicToPublish;
	
    private final EventProducer eventProducer;

    @Autowired
    EventMessageController(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }
            
    @PostMapping(path = "/send/message/{event}")
	public void sendEventMessage(@PathVariable String event) {
		this.eventProducer.sendEventMessage(topicToPublish, event);
	}
}
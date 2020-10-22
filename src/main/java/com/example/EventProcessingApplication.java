package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.domain.EventMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication(exclude = {JpaRepositoriesAutoConfiguration.class, KafkaAutoConfiguration.class
		})
public class EventProcessingApplication implements CommandLineRunner{
		
	public static void main(String[] args) {
		// Launch the application
		System.setProperty("javax.security.auth.useSubjectCredsOnly", "true");
		SpringApplication.run(EventProcessingApplication.class, args);
	}
	
	@Override
    public void run(String... strings) throws Exception {
	}
}

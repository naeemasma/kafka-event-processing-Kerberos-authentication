package com.example.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.example.constants.Constants;
import com.example.domain.EventMessage;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class ConsumerConfiguration {
	@Value("${bootstrap.servers}")
    private String bootstrapServers;
	@Value("${security.protocol}")
	private String securityProtocol;
	@Value("${ssl.truststore.location}")
	private String sslTruststoreLocation;
	@Value("${ssl.truststore.password}")
	private String sslTruststorePassword;
	@Value("${ssl.enabled.protocols}")
	private String sslEnabledProtocols;
	@Value("${ssl.truststore.type}")
	private String sslTruststoreType;
	@Value("${ssl.keystore.type}")
	private String sslKeystoreType;
	@Value("${ssl.keystore.location}")
	private String sslKeystoreLocation;
	@Value("${ssl.keystore.password}")
	private String sslKeyStorePassword;
	@Value("${ssl.key.password}")
	private String sslKeyPassword;
	@Value("${ssl.client.auth}")
	private Object sslClientAuth;    
	@Value("${sasl.mechanism}")
	private String saslMechanism;
	@Value("${sasl.kerberos.service.name}")
	private Object saslKerberosServiceName;
	@Value("${sasl.jaas.config}")
	private String saslJaasConfig;
	@Value("${spring.kafka.consumer.auto-offset-reset}")
	private String autoOffsetReset;
	
	public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        if(bootstrapServers.toLowerCase().startsWith(Constants.LOCALHOST))
     	   props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
         else{
         	props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, 
             	org.apache.kafka.common.serialization.ByteArrayDeserializer.class.getName());		
 	        props.put(Constants.SECURITY_PROTOCOL, securityProtocol);
 			props.put(Constants.SSL_TRUSTSORE_LOCATION, sslTruststoreLocation);
 			props.put(Constants.SSL_TRUSTSORE_PASSWORD, sslTruststorePassword);
 			props.put(Constants.SSL_ENABLED_PROTOCOLS, sslEnabledProtocols);
 			props.put(Constants.SSL_TRUSTSTORE_TYPE, sslTruststoreType);
 			props.put(Constants.SSL_KEYSTORE_TYPE, sslKeystoreType);
 			props.put(Constants.SSL_KEYSTORE_LOCATION, sslKeystoreLocation);
 			props.put(Constants.SSL_KEYSTORE_PASSWORD, sslKeyStorePassword);
 			props.put(Constants.SSL_KEY_PASSWORD, sslKeyPassword);
			props.put(Constants.SSL_CLIENT_AUTH, sslClientAuth);
			props.put(SaslConfigs.SASL_KERBEROS_SERVICE_NAME , saslKerberosServiceName);
	        props.put(SaslConfigs.SASL_MECHANISM, saslMechanism);
			props.put(SaslConfigs.SASL_JAAS_CONFIG, saslJaasConfig);
         }
        return props;
    }

    @Bean
    public ConsumerFactory<Long, EventMessage> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new LongDeserializer(),
                new JsonDeserializer<>(EventMessage.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, EventMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, EventMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}

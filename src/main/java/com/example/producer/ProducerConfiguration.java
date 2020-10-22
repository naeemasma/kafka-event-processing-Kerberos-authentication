package com.example.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.example.constants.Constants;
import com.example.domain.EventMessage;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class ProducerConfiguration {
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

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.CLIENT_ID_CONFIG,Constants.CLIENT_ID);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        if(bootstrapServers.toLowerCase().startsWith(Constants.LOCALHOST))
                props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
            else{
            	props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
            			org.apache.kafka.common.serialization.ByteArraySerializer.class.getName());
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
    public ProducerFactory<Long, EventMessage> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<Long, EventMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}

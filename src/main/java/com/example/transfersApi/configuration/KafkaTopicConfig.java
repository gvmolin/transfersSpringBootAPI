package com.example.transfersApi.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
@EnableKafka
public class KafkaTopicConfig { 
  
    @Bean 
    KafkaAdmin admin() { 
        Map<String, Object> configs = new HashMap<>(); 
        configs.put( 
            AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, 
            "localhost:9092"); 
        return new KafkaAdmin(configs); 
    } 
  
    @Bean 
    NewTopic createProcessingTransferTopic() { 
        return TopicBuilder.name("proccessing-transfer-topic") 
            .partitions(1) 
            .replicas(1) 
            .build(); 
    } 

    
  
}
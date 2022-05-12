package org.cscie88c.thermostat_producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring config that creates kafka topic
 */
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic thermoTopic() {
        return new NewTopic("thermoTopic", 1, (short) 1);
    }

}

package org.cscie88c.thermostat_producer.service;

import org.cscie88c.thermostat_producer.model.Thermostat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * A producer service that sends thermostat records to kafka
 */
@Service
public class ThermoProducer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC = "thermoTopic";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(Thermostat thermostat) {
        logger.info(String.format("### -> Producing thermostat status -> %s", thermostat.toString()));

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC, thermostat);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                logger.info(String.format("Sent message=[ %1$s ] with offset=[ %2$s ] "), thermostat.toString(),
                        result.getRecordMetadata().offset());

            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info(String.format("Unable to send message=[ %1$s ] due to: %2$s", thermostat.toString(),
                        ex.getMessage()));
            }

        });

    }

}

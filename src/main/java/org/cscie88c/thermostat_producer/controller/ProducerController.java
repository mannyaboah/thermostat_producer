package org.cscie88c.thermostat_producer.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.cscie88c.thermostat_producer.model.Thermostat;
import org.cscie88c.thermostat_producer.service.ThermoProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Rest controller used to create a single thermostat record,
 * Or generater a number of records per minute.
 */
@RestController
@RequestMapping(value = "kafka")
public class ProducerController {

    private static final Logger logger = LoggerFactory.getLogger(ProducerController.class);


    private final ThermoProducer thermoProducer;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ProducerController(ThermoProducer thermoProducerIn) {
        this.thermoProducer = thermoProducerIn;
    }

    @PostMapping(value = "/createRecord")
    @Operation(summary = "Send a thermostat record", description = "Sends a single record to Kafka", tags = {
            "Thermostat" })
    public void sendToKafka(
            @RequestParam("indoorTemp") int indoorTemp,
            @RequestParam("outdoorTemp") int outdoorTemp,
            @RequestParam("indoorHumid") int indoorHumid,
            @RequestParam("outdoorHumid") int outdoorHumid) {
        Thermostat therm = new Thermostat(indoorTemp,
                outdoorTemp,
                indoorHumid,
                outdoorHumid,
                LocalDateTime.now().format(formatter));

        this.thermoProducer.sendMessage(therm);
    }

    @PostMapping(value = "/generateRecords")
    @Operation(summary = "Send thermostat records", description = "Sends multiple records to Kafka", tags = {
            "Thermostat" })
    public void generateToKafka(
            @RequestParam("amount") int amount) {

        IntStream.range(0, amount).forEach(idx -> {
            this.thermoProducer.sendMessage(new Thermostat(ThreadLocalRandom.current().nextInt(67, 81),
                    ThreadLocalRandom.current().nextInt(67, 81),
                    ThreadLocalRandom.current().nextInt(67, 81),
                    ThreadLocalRandom.current().nextInt(67, 81),
                    LocalDateTime.now().format(formatter)));

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        logger.info(e.getMessage());
                        e.printStackTrace();
                    }
        });
    }
}

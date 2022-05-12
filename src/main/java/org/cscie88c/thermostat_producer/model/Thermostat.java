package org.cscie88c.thermostat_producer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Pojo for data exchage
 */
@Data
@AllArgsConstructor
public class Thermostat {
    int indoorTemp;
    int outdoorTemp;
    int indoorHumid;
    int outdoorHumid;
    String datetime;
}

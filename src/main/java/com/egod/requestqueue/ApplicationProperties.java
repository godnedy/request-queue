package com.egod.requestqueue;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties()
public class ApplicationProperties {
    private String fileName;
}
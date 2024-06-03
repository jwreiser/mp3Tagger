package com.goodforallcode.mp3Tagger.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "application")
@Component
public class ApplicationProperties {
}

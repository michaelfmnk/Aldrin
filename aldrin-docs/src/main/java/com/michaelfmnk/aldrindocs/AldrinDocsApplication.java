package com.michaelfmnk.aldrindocs;

import com.michaelfmnk.aldrindocs.properties.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class})
public class AldrinDocsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AldrinDocsApplication.class, args);
    }
}

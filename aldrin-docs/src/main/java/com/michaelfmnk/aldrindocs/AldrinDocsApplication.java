package com.michaelfmnk.aldrindocs;

import com.michaelfmnk.aldrindocs.properties.AuthProperties;
import com.michaelfmnk.aldrindocs.properties.BasicAuthProperties;
import com.michaelfmnk.aldrindocs.properties.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class, AuthProperties.class, BasicAuthProperties.class})
public class AldrinDocsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AldrinDocsApplication.class, args);
    }
}

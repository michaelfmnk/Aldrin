package com.michaelfmnk.aldrin;

import com.michaelfmnk.aldrin.storage.StorageProperties;
import com.michaelfmnk.aldrin.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class AldrinApplication {

	public static void main(String[] args) {
		SpringApplication.run(AldrinApplication.class, args);
	}

	@Bean
	public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
	}

	@Bean
	CommandLineRunner init(StorageService storageService){
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};

	}
}

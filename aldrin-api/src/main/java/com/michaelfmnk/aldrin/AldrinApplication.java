package com.michaelfmnk.aldrin;

import com.michaelfmnk.aldrin.props.AuthProperties;
import com.michaelfmnk.aldrin.props.MailjetProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableConfigurationProperties({AuthProperties.class, MailjetProperties.class})
public class AldrinApplication {

	public static void main(String[] args) {
		SpringApplication.run(AldrinApplication.class, args);
	}


	/**
	 * configuring cors access
	 */
	@Bean
	public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
	}

}

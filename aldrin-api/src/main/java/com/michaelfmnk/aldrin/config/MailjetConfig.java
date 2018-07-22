package com.michaelfmnk.aldrin.config;

import com.mailjet.client.MailjetClient;
import com.michaelfmnk.aldrin.props.MailjetProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class MailjetConfig {

    private final MailjetProperties jetmailProperties;

    @Bean
    public MailjetClient mailjetClient() {
        return new MailjetClient(jetmailProperties.getPublicKey(), jetmailProperties.getPrivateKey());
    }
}

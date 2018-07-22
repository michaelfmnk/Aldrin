package com.michaelfmnk.aldrin.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("mailjet")
public class MailjetProperties {
    private String publicKey;
    private String privateKey;
}

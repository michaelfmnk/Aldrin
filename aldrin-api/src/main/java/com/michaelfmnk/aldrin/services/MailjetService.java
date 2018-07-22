package com.michaelfmnk.aldrin.services;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.resource.Email;
import com.michaelfmnk.aldrin.utils.MessagesService;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

@Service
@CommonsLog
@AllArgsConstructor
public class MailjetService {

    private final MailjetClient client;
    private final MessagesService messagesService;
    public static final String FROM = "michael.fmnk@gmail.com";
    public static final String FROM_NAME = "Aldrin";

    public void sendEmail(String to, String subject, String text) {
        try {
            MailjetRequest request = new MailjetRequest(Email.resource)
                    .property(Email.FROMEMAIL, FROM)
                    .property(Email.FROMNAME, FROM_NAME)
                    .property(Email.SUBJECT, subject)
                    .property(Email.TEXTPART, text)
                    .property(Email.TO, to);
            client.post(request);
        } catch (Exception e) {
            log.error(e.getStackTrace());
            throw new RuntimeException(messagesService.getMessage("mailjet.fail.message"));
        }

    }
}

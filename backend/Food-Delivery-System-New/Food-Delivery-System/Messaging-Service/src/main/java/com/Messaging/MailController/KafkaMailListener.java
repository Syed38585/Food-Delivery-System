package com.Messaging.MailController;

import com.Messaging.Dto.MailDto;
import com.Messaging.MailService.Mailservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
public class KafkaMailListener {

    @Autowired
    private Mailservice service;

    @KafkaListener(topics = "kafkaMail", groupId = "email_group")        // api to send mail
    public void sendMail(String messageJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            MailDto dto = mapper.readValue(messageJson, MailDto.class);

            if (dto.getUser_mail() == null || dto.getUser_mail().isBlank()) {
                System.err.println("Invalid email in Kafka message: " + messageJson);
                return;
            }

            service.sendmail(dto);

        } catch (Exception e) {
            System.err.println("Failed to process Kafka message: " + e.getMessage());
        }

//    @PostMapping("/send/mail")
//    public ResponseEntity<?> sendmail(MailDto dto){
//        service.sendmail(dto);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }
}

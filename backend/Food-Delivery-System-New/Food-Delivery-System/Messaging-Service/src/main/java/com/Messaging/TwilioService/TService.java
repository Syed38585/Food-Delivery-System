package com.Messaging.TwilioService;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TService {

    @Value("${twilio.auth_token}")
    private String AUTH_TOKEN;

    @Value("${twilio.auth_secret}")
    private String AUTH_SECRET;

    @Value("${twilio.phone_no}")
    private String sender_no;


    @PostConstruct
    public void setup(){
        Twilio.init(AUTH_SECRET,AUTH_TOKEN);
    }

    ExecutorService executors = Executors.newFixedThreadPool(10);

    @Async
       public void sendSms(String to, String messageBody) {
        executors.submit(() -> {
            Message message = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(sender_no),
                    messageBody
            ).create();
        });
    }
}

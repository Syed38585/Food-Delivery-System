package com.Messaging.TwilioController;

import com.Messaging.Dto.TwilioDto;
import com.Messaging.TwilioService.TService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TController {

    TService twilioService;

    TController(TService twilioService){
        this.twilioService = twilioService;
    }


        @KafkaListener(topics = "kafka", groupId = "sms_group")    // api to send sms
        @PostMapping("/send")
        public void sendSms(@RequestBody String messageJson) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                TwilioDto twilioDto = mapper.readValue(messageJson, TwilioDto.class);
                twilioService.sendSms(twilioDto.getReceiverNo(), twilioDto.getContent());
            } catch (JsonProcessingException e) {
                System.err.println("Failed to parse Kafka message: " + e.getMessage());
            }
        }

//    @PostMapping("/send")
//    public ResponseEntity<?> sendSms(@RequestBody TwilioDto dto){
//        System.out.println(dto);
//        System.out.println(dto.getReceiverNo());
//        System.out.println(dto.getContent());
//     twilioService.sendSms(dto.getReceiverNo(),dto.getContent());
//     return ResponseEntity.ok(HttpStatus.OK);
//    }
    }

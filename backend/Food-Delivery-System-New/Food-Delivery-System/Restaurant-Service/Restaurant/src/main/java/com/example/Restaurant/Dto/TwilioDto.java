package com.example.Restaurant.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TwilioDto {    //DTO for sms
    String receiverNo;
    String content;
}



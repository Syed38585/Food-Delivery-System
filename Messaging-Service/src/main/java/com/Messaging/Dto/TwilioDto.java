package com.Messaging.Dto;

import lombok.Data;

@Data
public class TwilioDto {
    String senderNo;
    String receiverNo;
    String content;
}

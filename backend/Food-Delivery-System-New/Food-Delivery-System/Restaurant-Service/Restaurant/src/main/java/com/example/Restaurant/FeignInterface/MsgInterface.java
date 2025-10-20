package com.example.Restaurant.FeignInterface;

import com.example.Restaurant.Dto.TwilioDto;
import com.example.Restaurant.Dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MSG-SERVICE")
public interface MsgInterface {        // feign interface for messaging service
    @PostMapping("/send/mail")
    public void sendmail(@RequestBody UserDto dto);

    @PostMapping("/send")
    public void sendSms(@RequestBody TwilioDto dto);
}

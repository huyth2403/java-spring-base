package com.controller;

import com.dto.UserDto;
import com.response.BaseResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/index")
    public BaseResponse helloAdmin(@AuthenticationPrincipal UserDto userDto){
        return BaseResponse.success("Hello " + userDto.getUsername());
    }
}

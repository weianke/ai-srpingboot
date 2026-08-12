package com.example.aispringboot.controller;

import com.example.aispringboot.common.Result;
import com.example.aispringboot.dto.command.UserLoginCommandDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class User {

    // 登录接口
    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody UserLoginCommandDTO commandDTO) {
        System.out.println(commandDTO.getUsername());
        System.out.println(commandDTO.getPassword());

        return null;
    }

}

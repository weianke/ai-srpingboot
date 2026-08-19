package com.example.aispringboot.controller;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.aispringboot.common.Result;
import com.example.aispringboot.dto.command.UserLoginCommandDTO;
import com.example.aispringboot.dto.command.UserRegisterCommandDTO;
import com.example.aispringboot.dto.response.UserLoginResponseDTO;
import com.example.aispringboot.service.UserService;
import com.example.aispringboot.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class User {
    // 注入service
    @Resource
    private UserService userService;

    // 登录接口
    @PostMapping("/login")
    public Result<UserLoginResponseDTO> login(@Valid @RequestBody UserLoginCommandDTO commandDTO) {
        UserLoginResponseDTO result = userService.login(commandDTO);

        return Result.ok(result);
    }

    // 注册接口
    @PostMapping("/add")
    public Result<UserLoginResponseDTO.UserDetailResponseDTO> register(@Valid @RequestBody UserRegisterCommandDTO commandDTO) {
        UserLoginResponseDTO.UserDetailResponseDTO result = userService.register(commandDTO);

        return Result.ok(result);
    }

    // 获取当前用户信息
    @GetMapping("/current")
    public Result<UserLoginResponseDTO.UserDetailResponseDTO> getCurrentUser() {
        // 获取token解析出用户的id
        String token = JwtTokenUtil.getCurrentToken();
        DecodedJWT jwt = JwtTokenUtil.verifyToken(token);
        Long userId = jwt.getClaim("userId").asLong();

        // 调用service层获取用户详情
        UserLoginResponseDTO.UserDetailResponseDTO result = userService.getUserById(userId);
        System.out.println(JSONUtil.parseObj(result));
        return Result.ok(result);
    }
}

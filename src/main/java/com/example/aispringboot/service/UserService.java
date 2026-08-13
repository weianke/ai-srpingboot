package com.example.aispringboot.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.aispringboot.common.Result;
import com.example.aispringboot.dto.command.UserLoginCommandDTO;
import com.example.aispringboot.dto.response.UserLoginResponseDTO;
import com.example.aispringboot.entity.User;
import com.example.aispringboot.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;


    public Result<UserLoginResponseDTO> login(UserLoginCommandDTO commandDTO) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, commandDTO.getUsername())
                .or()
                .eq(User::getEmail, commandDTO.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);

        // 没查到用户 → 返回失败
        if (user == null) {
            return Result.error();
        }

        // 查到用户 → 成功，构造返回DTO
        UserLoginResponseDTO dto = new UserLoginResponseDTO();
        // 按需赋值 dto.setXxx(...)
        return Result.ok(dto);
    }
}
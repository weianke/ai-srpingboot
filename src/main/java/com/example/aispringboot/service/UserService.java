package com.example.aispringboot.service;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.aispringboot.common.Result;
import com.example.aispringboot.dto.command.UserLoginCommandDTO;
import com.example.aispringboot.dto.command.UserRegisterCommandDTO;
import com.example.aispringboot.dto.response.UserLoginResponseDTO;
import com.example.aispringboot.entity.User;
import com.example.aispringboot.enumClass.UserType;
import com.example.aispringboot.exception.BusinessException;
import com.example.aispringboot.mapper.UserMapper;
import com.example.aispringboot.service.convert.UserConvert;
import com.example.aispringboot.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 登录
    public UserLoginResponseDTO login(UserLoginCommandDTO commandDTO) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, commandDTO.getUsername())
                .or()
                .eq(User::getEmail, commandDTO.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);

        // 没查到用户 → 返回失败
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 验证密码
        String inputPassword = commandDTO.getPassword().trim();
        if (!passwordEncoder.matches(inputPassword, user.getPassword())){
            throw new BusinessException("密码错误");
        }

        // 检查用户的状态
        if(!user.isActive()) {
            throw new BusinessException("用户已被禁用，请联系管理员");
        }

        // 生成jwt token
        String token = JwtTokenUtil.generateToken(user.getId(), user.getUsername(), user.getUserType());
        System.out.println(token);
        UserLoginResponseDTO.UserDetailResponseDTO userInfo = UserConvert.entityToDetailResponse(user);

        return UserConvert.entityToLoginResponse(token, userInfo);
    }

    // 注册
    public UserLoginResponseDTO.UserDetailResponseDTO register(UserRegisterCommandDTO commandDTO) {
        System.out.println(JSONUtil.parseObj(commandDTO));
        // 验证密码是否一致
        if (!commandDTO.getPassword().equals(commandDTO.getConfirmPassword())) {
            throw new BusinessException("两次输入密码不一致");
        }

        // 检查用户名是否已经存在
        LambdaQueryWrapper<User> userNameQueryWrapper = new LambdaQueryWrapper<>();
        userNameQueryWrapper.eq(User::getUsername, commandDTO.getUsername());
        if (userMapper.selectCount(userNameQueryWrapper) > 0) {
            throw new BusinessException("用户名已经存在");
        }

        // 检查邮箱是否存在
        LambdaQueryWrapper<User> emailQueryWrapper = new LambdaQueryWrapper<>();
        emailQueryWrapper.eq(User::getEmail, commandDTO.getEmail());

        if (userMapper.selectCount(emailQueryWrapper) > 0) {
            throw new BusinessException("邮箱已经存在");
        }

        // 用户类型
        if (!UserType.isValidCode(commandDTO.getUserType())) {
            throw new BusinessException("无效的用户类型");
        }

        // 创建用户
        String password = commandDTO.getPassword().trim();
        String encodePassword = passwordEncoder.encode(password);

        User user = UserConvert.registerCommandToEntity(commandDTO, encodePassword);

        // 插入数据库
        userMapper.insert(user);

        return UserConvert.entityToDetailResponse(user);
    }

    public UserLoginResponseDTO.UserDetailResponseDTO getUserById(Long userId) {
        User user = userMapper.selectById(userId);
        if(user == null) {
            throw new BusinessException("用户不存在");
        }
        return UserConvert.entityToDetailResponse(user);
    }
}
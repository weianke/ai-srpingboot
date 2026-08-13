package com.example.aispringboot.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserLoginResponseDTO {
    /**
     * token
     */
    private String token;
    /**
     * 用户类型
     */
    private String roleType;
    private UserDetailResponseDTO userInfo;

    /**
     * 用户详细信息内部DTO
     */
    @Data
    public static class UserDetailResponseDTO {
        /**
         * 用户ID
         */
        private Long id;

        /**
         * 用户名
         */
        private String username;

        /**
         * 邮箱
         */
        private String email;

        /**
         * 昵称
         */
        private String nickname;

        /**
         * 头像地址
         */
        private String avatar;

        /**
         * 手机号码
         */
        private String phone;

        /**
         * 性别编码：1男，2女，0未知
         */
        private Integer gender;

        /**
         * 性别展示文字
         */
        private String genderDisplayName;

        /**
         * 生日 yyyy-MM-dd
         */
        private String birthday;

        /**
         * 用户类型编码
         */
        private Integer userType;

        /**
         * 用户类型展示名称
         */
        private String userTypeDisplayName;

        /**
         * 用户状态编码
         */
        private Integer status;

        /**
         * 状态展示文字
         */
        private String statusDisplayName;

        /**
         * 对外展示名称
         */
        private String displayName;

        /**
         * 创建时间
         */
        private LocalDateTime createdAt;

        /**
         * 更新时间
         */
        private LocalDateTime updatedAt;
    }
}
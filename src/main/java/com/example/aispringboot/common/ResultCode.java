package com.example.aispringboot.common;

import lombok.Getter;

/**
 * 全局统一响应状态码枚举
 * 配合Result统一返回类使用，管理所有接口业务编码与提示文案
 */
@Getter
public enum ResultCode {
    // ========== 通用响应码 ==========
    /** 请求成功 */
    SUCCESS("200", "操作成功"),
    /** 通用失败 */
    ERROR("-1", "操作失败"),
    /** 登录认证失效 */
    UNAUTHORIZED("401", "暂未登录或token已经过期"),
    /** 服务端系统异常 */
    SYSTEM_ERROR("500", "系统错误"),

    // ========== 参数校验相关错误 ==========
    /** 参数通用错误 */
    PARAM_ERROR("400", "参数错误"),
    /** 缺少必填参数 */
    PARAM_MISSING("4001", "缺少必要参数"),
    /** 参数格式非法 */
    PARAM_INVALID("4002", "参数格式不正确"),

    // ========== 文件操作相关错误 ==========
    /** 文件不存在 */
    FILE_NOT_FOUND("5001", "文件不存在"),
    /** 文件上传失败 */
    FILE_UPLOAD_FAILED("5002", "文件上传失败"),
    /** 文件删除失败 */
    FILE_DELETE_FAILED("5003", "文件删除失败"),
    /** 文件超出大小限制 */
    FILE_SIZE_EXCEEDED("5004", "文件大小超过限制"),
    /** 不支持该文件后缀类型 */
    FILE_TYPE_NOT_SUPPORTED("5005", "不支持的文件类型"),
    /** 文件名非法 */
    FILE_NAME_INVALID("5006", "文件名不合法"),
    /** 文件内容违规/损坏 */
    FILE_CONTENT_INVALID("5007", "文件内容不合法"),
    /** 文件落地保存失败 */
    FILE_SAVE_FAILED("5008", "文件保存失败"),

    // ========== 业务逻辑相关错误 ==========
    /** 通用业务异常 */
    BUSINESS_ERROR("6000", "业务处理失败"),
    /** 用户名重复 */
    ACCOUNT_SAME("6001", "用户名已存在"),
    /** 查询用户不存在 */
    USER_NOT_EXIST("6002", "用户不存在"),

    // ========== Token、权限认证相关 ==========
    /** Token无效 */
    TOKEN_INVALID("A0230", "token无效"),
    /** Token已过期 */
    TOKEN_EXPIRED("A0230", "token已过期"),
    /** Token在黑名单中 */
    TOKEN_BLOCKED("A0230", "token已加入黑名单"),
    /** Token禁止访问 */
    TOKEN_ACCESS_FORBIDDEN("A0231", "token已被禁止访问"),
    /** 权限校验异常 */
    AUTHORIZED_ERROR("A0300", "访问权限异常"),
    /** 无访问权限 */
    ACCESS_UNAUTHORIZED("A0301", "访问未授权");


    /** 响应编码 */
    private final String code;
    /** 前端展示提示信息 */
    private final String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
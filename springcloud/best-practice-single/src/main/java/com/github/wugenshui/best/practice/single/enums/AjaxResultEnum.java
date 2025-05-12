package com.github.wugenshui.best.practice.single.enums;

/**
 * 请求响应状态码与消息对应的枚举
 *
 * @author : chenbo
 * @date : 2019-12-07
 */
public enum AjaxResultEnum {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 非法请求
     */
    INVALID(400, "非法请求"),

    /**
     * 未授权
     */
    UNAUTHORIZED(401, "未授权"),

    /**
     * 无权限访问
     */
    NO_ACCESS(403, "无权限访问"),

    /**
     * 请求资源不存在
     */
    NOT_FOUND(404, "请求资源不存在"),

    /**
     * 请求方法错误
     */
    METHOD_NOT_ALLOWED(405, "请求方法错误"),

    /**
     * 服务器错误
     */
    ERROR(500, "服务器错误"),

    /**
     * 网关错误
     */
    ERROR_GATEWAY(502, "网关错误");

    /**
     * 响应状态
     */
    private final Integer state;

    /**
     * 响应消息
     */
    private final String msg;

    AjaxResultEnum(Integer state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public Integer getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }
}

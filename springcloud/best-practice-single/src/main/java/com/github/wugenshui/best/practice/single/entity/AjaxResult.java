package com.github.wugenshui.best.practice.single.entity;

import com.github.wugenshui.best.practice.single.enums.AjaxResultEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Ajax请求响应类
 *
 * @author : chenbo
 * @date : 2019-12-07
 */
@Schema
public class AjaxResult<T> {
    /**
     * 返回状态:参照HttpResultEnum枚举
     */
    @Schema(description = "返回状态:参照HttpResultEnum枚举")
    private int state;

    /**
     * 返回的消息提示
     */
    @Schema(description = "返回的消息提示")
    private String msg;

    /**
     * 返回的数据
     */
    @Schema(description = "返回的数据")
    private T data;

    public AjaxResult(int state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public AjaxResult(int state, String msg, T data) {
        this.state = state;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回成功 默认提示
     *
     * @return
     */
    public static AjaxResult success() {
        AjaxResultEnum model = AjaxResultEnum.SUCCESS;
        return new AjaxResult(model.getState(), model.getMsg());
    }

    /**
     * 返回成功 默认提示
     *
     * @param data
     * @return
     */
    public static <T> AjaxResult success(T data) {
        AjaxResultEnum model = AjaxResultEnum.SUCCESS;
        return new AjaxResult(model.getState(), model.getMsg(), data);
    }

    /**
     * 返回成功 自定义提示
     *
     * @param message
     * @param data
     * @return
     */
    public static <T> AjaxResult success(String message, T data) {
        return new AjaxResult(AjaxResultEnum.SUCCESS.getState(), message, data);
    }

    /**
     * 返回系统异常错误
     *
     * @return
     */
    public static AjaxResult error() {
        AjaxResultEnum model = AjaxResultEnum.ERROR;
        return new AjaxResult(model.getState(), model.getMsg());
    }

    /**
     * 返回失败 自定义错误
     *
     * @param message
     * @return
     */
    public static AjaxResult error(String message) {
        return new AjaxResult(AjaxResultEnum.ERROR.getState(), message);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

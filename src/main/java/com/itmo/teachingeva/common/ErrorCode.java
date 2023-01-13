package com.itmo.teachingeva.common;


public enum ErrorCode {

    /**
     * 账户相关
     */
    USER_NOT_EXIT(2000, "账户不存在！"),
    ACCOUNT_EMPTY(2001, "账号密码为空！"),
    PASSWORD_ERROR(2002, "密码错误！"),
    USERNAME_FORMAT_ERROR(2003, "账号格式不规范！"),
    PASSWORD_FORMAT_ERROR(2004, "密码格式不规范！"),
    USERNAME_EXIT(2005, "账户已存在！"),

    NO_PERMISSION(2006, "您没有权限！");



    private Integer code;

    private String msg;




    private ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}

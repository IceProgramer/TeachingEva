package com.itmo.teachingeva.common;

/**
 * 状态码
 *
 * @author chenjiahan
 */
public enum ErrorCode {

    /**
     * 成功
     */
    SUCCESS(200, "success", ""),

    /**
     * 管理员账户相关
     */
    USER_NOT_EXIT(2000, "账户不存在！", ""),
    ACCOUNT_EMPTY(2001, "账号密码为空！", ""),
    PASSWORD_ERROR(2002, "密码错误！", ""),
    USERNAME_FORMAT_ERROR(2003, "账号格式不规范！", ""),
    PASSWORD_FORMAT_ERROR(2004, "密码格式不规范！", ""),
    USERNAME_EXIT(2005, "账户已存在！", ""),

    NO_PERMISSION(2006, "您没有权限！", ""),

    /**
     * 学生相关
     */
    STUDENT_EMPTY(3000, "无学生信息", ""),
    STUDENT_EXIT(3001, "该学生已存在", ""),
    STUDENT_NO_EXIT(3002, "学生不存在", ""),
    STUDENT_GRADUATE(3003, "学生已毕业", ""),

    /**
     * 教师相关
     */
    TEACHER_EMPTY(4000, "无教师信息", ""),
    TEACHER_EXIT(4001, "教师已存在", ""),
    TEACHER_NO_EXIT(4002, "教师不存在", ""),

    /**
     * 课程相关
     */
    COURSE_EMPTY(6000, "无课程信息", ""),
    COURSE_EXIT(6001, "该课程已存在", ""),
    COURSE_NO_EXIT(6002, "课程不存在", ""),


    SYSTEM_ERROR(5000, "系统内部异常", ""),
    FILE_EMPTY(5001, "文件为空", ""),

    /**
     * 评测相关
     */
    EVALUATION_EMPTY(6000, "无评测信息", ""),
    EVALUATION_EXIT(6001, "该评测已存在", ""),
    EVALUATION_NO_EXIT(6002, "评测不存在", ""),
    EVALUATION_SYSTEM_EMPTY(6003, "评测系统为空", "");




    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 状态码信息
     */
    private final String msg;

    /**
     * 状态码描述
     */
    private final String description;


    ErrorCode(int code, String msg, String description) {
        this.code = code;
        this.msg = msg;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getDescription() {
        return description;
    }


}

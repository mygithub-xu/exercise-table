package com.dhlg.utils;

import com.dhlg.module.common.ParamIsNullException;
import lombok.Data;

/**
 * 后端返回结果的封装类
 */
@Data
public class Result extends ParamIsNullException {

    /**
     * 状态码，是否成功
     */
    String code = "200";

    /**
     * 返回的数据
     */
     Object body;

    /**
     * 提示信息
     */
     String message = "访问接口成功";


    public Result() {
    }

    public Result(Object body) {
        this.body = body;
    }

    public Result(String code, Object body, String message) {
        this.code = code;
        this.body = body;
        this.message = message;
    }

    public Result(String code, Object body) {
        this.code = code;
        this.body = body;
    }


    /**
     * 构建失败的结果
     * @param body
     */
    public void buildFailedResult(String code,String body,String message){
        this.code = code;
        this.body = body;
        this.message = message;
    }


    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", body=" + body +
                ", message='" + message + '\'' +
                '}';
    }


    public static Result error(String message) {
        return new Result("500",message,message);
    }

    public static Result success(String message) {
        return new Result("200",message,message);
    }

    public static Result success(Object body) {
        return new Result("200",body);
    }

    public static Result success(Object body,String message) {
        return new Result("200",body,message);
    }

}